package com.library.bookmicroservice.services.reservation;

import com.library.bookmicroservice.exceptions.ReservationLimitException;
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

    @Override
    public Reservation getReservation(Long id) {
        return reservationRepository.getOne(id);
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
    public Reservation createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = reservationMapper.reservatioDtoToReservation(reservationDTO);
        List<Reservation> reservationList = reservationRepository.findByBookID(reservationDTO.bookID);
        Book book = bookRepository.getOne(Long.valueOf(reservationDTO.bookID));

        if (!checkReservationCountByBook(reservationDTO))
            throw new ReservationLimitException("Aucune réservation supplémentaire n'est disponible pour cet ouvrage.");
        if (checkUserDoubleReservation(reservationDTO))
            throw new ReservationLimitException("Vous ne pouvez réserver qu'un exemplaire de cet ouvrage");
        if (checkUserApplyForBorrowBook(reservationDTO))
            throw new ReservationLimitException("Vous ne pouvez pas réserver un livre que vous êtes actuellement entrain d'emprunter");
        else {
            book.setAvaible(false);
            bookRepository.save(book);
            if(reservationList.isEmpty()) {
                Date today = new Date();
                reservation.setDate(today);
                Email email = emailService.createEmailInformations(reservation);
                emailService.sendEmailReservation(email);
            }
        }

        return reservationRepository.save(reservation);
    }

    @Override
    public void updateReservation(ReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.getOne(reservationDTO.getId());
        if(reservation.getId() == null) {
            throw new RuntimeException("La réservation de ce livre n'existe pas ou elle est introuvable");
        } else {
            reservationMapper.updateReservationFromReservationDTO(reservationDTO, reservation);
            reservationRepository.save(reservation);

        }
    }

    @Override
    public void deleteReservation(Long id) { reservationRepository.deleteById(id); }

    public void updateBookReservation(String bookID) {
        Book book = bookRepository.getOne(Long.valueOf(bookID));
        List<Reservation> reservationList = reservationRepository.findAllByBookID(bookID);

        if(!reservationList.isEmpty()) {
            book.setAvaible(false);
            bookRepository.save(book);

            Reservation reservation = reservationList.get(0);
            Date today = new Date();
            reservation.setDate(today);
            reservationRepository.save(reservation);

            Email email = emailService.createEmailInformations(reservation);
            emailService.sendEmailReservation(email);

        } else {
            book.setAvaible(true);
           bookRepository.save(book);
        }

    }

    private boolean checkReservationCountByBook(ReservationDTO reservationDTO) {
        List<Reservation> reservations = this.reservationRepository.findByBookID(reservationDTO.bookID);
        return reservations.size() <= 1;
    }

    private boolean checkUserDoubleReservation(ReservationDTO reservationDTO) {
        boolean check = false;
        List<Reservation> reservations = this.reservationRepository.findByBookID(reservationDTO.bookID);
        for (Reservation reservation : reservations) {
            check = reservation.getUserEmail().equals(reservationDTO.userEmail) && reservation.getBookTitle().equals(reservationDTO.bookTitle);
        }
        return check;
    }

    private boolean checkUserApplyForBorrowBook(ReservationDTO reservationDTO) {
        Borrow borrow = BorrowDatabaseConnect.getBorrowFromDB(reservationDTO.getBookID());
        if (borrow.getBookID() != null) {
            User user = UserDabataseConnect.getUserFromDB(borrow.getUserID());
            return user.getEmail().equals(reservationDTO.userEmail);
        }
        return false;
    }

}
