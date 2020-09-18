package com.library.bookmicroservice.services.reservation;

import com.library.bookmicroservice.exceptions.ReservationLimitException;
import com.library.bookmicroservice.exceptions.ReservationNotFoundException;
import com.library.bookmicroservice.model.*;
import com.library.bookmicroservice.repository.BookRepository;
import com.library.bookmicroservice.repository.ReservationRepository;
import com.library.bookmicroservice.services.util.BorrowDatabaseConnect;
import com.library.bookmicroservice.services.util.EmailService;
import com.library.bookmicroservice.services.util.UserDabataseConnect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private  ReservationRepository reservationRepository;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    UserDabataseConnect userDabataseConnect;

    @Autowired
    BorrowDatabaseConnect borrowDatabaseConnect;

    @Override
    public Optional<Reservation> getReservation(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> getReservationsByBookID(String bookID) {
        return reservationRepository.findAllByBookID(bookID);
    }

    @Override
    public List<Reservation> getReservationByUserEmail(String email) {
        return reservationRepository.findAllByUserEmailIs(email);
    }

    @Override
    public Reservation createReservation(ReservationDTO reservationDTO) {
        List<Reservation> reservationList = reservationRepository.findAllByBookID(reservationDTO.getBookID());
        Book book = bookRepository.getOne(Long.valueOf(reservationDTO.getBookID()));
        Reservation reservation = reservationMapper.reservatioDtoToReservation(reservationDTO);

        if (!checkReservationCountByBook(reservationDTO))
            throw new ReservationLimitException("Aucune réservation supplémentaire n'est disponible pour cet ouvrage.");
        if (checkUserDoubleReservation(reservationDTO))
            throw new ReservationLimitException("Vous ne pouvez réserver qu'un exemplaire de cet ouvrage");
        if (checkUserApplyForBorrowBook(reservationDTO))
            throw new ReservationLimitException("Vous ne pouvez pas réserver un livre que vous êtes actuellement entrain d'emprunter");

        else {
            if(reservationList.isEmpty() && book.getAvaible()) {
                Date today = new Date();
                reservation.setDate(today);
                reservation.setReservationPosition(1);
                createAndSendEmail(reservation);
            }

            if(reservationList.isEmpty() && !book.getAvaible()) {
                setReservationPosition(reservationDTO, reservation, 1);
            }

            if(!reservationList.isEmpty()) {
                setReservationPosition(reservationDTO, reservation, 2);
            }

            book.setAvaible(false);
            bookRepository.save(book);
        }

        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(ReservationDTO reservationDTO) {
        Optional<Reservation> reservationOptional = getReservation(reservationDTO.getId());
        Reservation reservation = null;

        if(reservationOptional.isPresent()) {
            reservation = new Reservation(
                    reservationOptional.get().getId(),
                    reservationOptional.get().getDate(),
                    reservationOptional.get().getUserEmail(),
                    reservationOptional.get().getBookID(),
                    reservationOptional.get().getBookTitle(),
                    reservationOptional.get().getBookReturn(),
                    reservationOptional.get().getReservationPosition()

            );
        }
        if(reservation == null) {
            throw new ReservationNotFoundException("La réservation de ce livre n'existe pas ou elle est introuvable");
        } else {
            reservationMapper.updateReservationFromReservationDTO(reservationDTO, reservation);
            reservationRepository.save(reservation);
        }
        return reservation;
    }

    @Override
    public Long deleteReservation(Long id) {
        reservationRepository.deleteById(id);
        return id;
    }

    public void updateBookReservation(String bookID) {
        Book book = bookRepository.getOne(Long.valueOf(bookID));
        Borrow borrow = borrowDatabaseConnect.getBorrowFromDBByBookID(bookID);
        List<Reservation> reservationList = reservationRepository.findAllByBookID(bookID);
        Date today = new Date();

        if(!reservationList.isEmpty()) {
            book.setAvaible(false);
            bookRepository.save(book);
            Reservation reservation = reservationList.get(0);
            reservation.setBookReturn(updateAvaibleDate(reservation));
            reservation.setReservationPosition(1);

            if(borrow.getId() == null) {
                reservation.setDate(today);
                Email email = createAndSendEmail(reservation);
            }
            reservationRepository.save(reservation);
        } else if(borrow.getId() == null){
            book.setAvaible(true);
            bookRepository.save(book);
        }
    }

    private Email createAndSendEmail(Reservation reservation) {
        Email email = emailService.createEmailInformations(reservation);
        emailService.sendEmailReservation(email);
        return email;
    }

    private void setReservationPosition(ReservationDTO reservationDTO, Reservation reservation, int i) {
        reservation.setReservationPosition(i);
        reservation.setBookReturn(updateAvaibleDateWithDTO(reservationDTO));
    }

    private boolean checkReservationCountByBook(ReservationDTO reservationDTO) {
        List<Reservation> reservations = this.reservationRepository.findAllByBookID(reservationDTO.getBookID());
        return reservations.size() <= 1;
    }

    private boolean checkUserDoubleReservation(ReservationDTO reservationDTO) {
        boolean check = false;
        List<Reservation> reservations = this.reservationRepository.findAllByBookID(reservationDTO.getBookID());
        for (Reservation reservation : reservations) {
            check = reservation.getUserEmail().equals(reservationDTO.getUserEmail()) && reservation.getBookTitle().equals(reservationDTO.getBookTitle());
        }
        return check;
    }

    private boolean checkUserApplyForBorrowBook(ReservationDTO reservationDTO) {
        boolean check = false;
        User user = userDabataseConnect.getUserFromDBByEmail(reservationDTO.getUserEmail());
        List<Book> books = bookRepository.findByTitle(reservationDTO.getBookTitle());

        for (Book book : books) {
            Borrow borrow = borrowDatabaseConnect.getBorrowFromDBByBookID(String.valueOf(book.getId()));

            if(borrow.getBookID() != null) {
                check = borrow.getUserID().equals(String.valueOf(user.getId()));
                return check;
            }
        }
        return false;
    }

    private Date updateAvaibleDateWithDTO(ReservationDTO reservationDTO) {
        Borrow borrow = borrowDatabaseConnect.getBorrowFromDBByBookID(reservationDTO.getBookID());
        if(borrow.getIsExtend() != null) return borrow.getIsExtend() ? borrow.getDateExtend() : borrow.getDateEnd();
        else return null;
    }

    private Date updateAvaibleDate(Reservation reservation) {
        Borrow borrow = borrowDatabaseConnect.getBorrowFromDBByBookID(reservation.getBookID());
        if(borrow.getIsExtend() != null) return borrow.getIsExtend() ? borrow.getDateExtend() : borrow.getDateEnd();
        else return null;
    }

}
