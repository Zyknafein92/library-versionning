package com.library.bookmicroservice.services.reservation;

import com.library.bookmicroservice.exceptions.ReservationLimitException;
import com.library.bookmicroservice.exceptions.ReservationNotFoundException;
import com.library.bookmicroservice.model.*;
import com.library.bookmicroservice.repository.BookRepository;
import com.library.bookmicroservice.repository.ReservationRepository;
import com.library.bookmicroservice.services.util.BorrowDatabaseConnect;
import com.library.bookmicroservice.services.util.EmailService;
import com.library.bookmicroservice.services.util.UserDabataseConnect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    ReservationRepository repository;

    @Mock
    UserDabataseConnect userDabataseConnect;

    @Mock
    BorrowDatabaseConnect borrowDatabaseConnect;

    @Mock
    BookRepository bookRepository;

    @Mock
    EmailService emailService;

    @Mock
    ReservationMapper mapper;

    @InjectMocks
    ReservationServiceImpl service;

    Book bookAvaible;

    Borrow borrow;

    User user;
    User user2;

    Email email;

    Reservation reservation;
    Reservation reservation2;

    ReservationDTO rDTO;
    ReservationDTO rDTO2;

    List<Reservation> reservationList = new ArrayList<>();

    SimpleDateFormat formater;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        formater = new SimpleDateFormat("dd-MM-yy");
        bookAvaible = Book.builder().id((long) 1).title("testBook").author("testBook").description("testBook").editor("testBook").parution(new Date()).gender("testBook").picture("testBook").avaible(true).libraryID("1").build();
        reservation = Reservation.builder().id((long) 1).bookID("1").bookTitle("testBook").bookReturn(null).date(null).userEmail("test@gmail.com").reservationPosition(null).build();
        rDTO = ReservationDTO.builder().id((long) 1).bookID("1").bookTitle("testBook").bookReturn(null).date(null).userEmail("test@gmail.com").reservationPosition(null).build();
        reservation2 =  Reservation.builder().id((long) 1).bookID("1").bookTitle("testBook").bookReturn(null).date(null).userEmail("test2@gmail.com").reservationPosition(null).build();
        rDTO2 = ReservationDTO.builder().id((long) 1).bookID("1").bookTitle("testBook").bookReturn(null).date(null).userEmail("test2@gmail.com").reservationPosition(null).build();
        user = User.builder().email("test@gmail.com").id((long)1).build();
        user2 = User.builder().email("test2@gmail.com").id((long)2).build();
    }

    @Test
    void getReservation_GivenReservation_ReturnReservation() {
        when(repository.findById((long) 1)).thenReturn(java.util.Optional.ofNullable(reservation));
        assertThat(service.getReservation((long) 1)).isEqualTo(java.util.Optional.ofNullable(reservation));
    }

    @Test
    void getReservations_GivenList_ReturnList() {
        when(repository.findAll()).thenReturn(reservationList);
        assertThat(service.getReservations()).isEqualTo(reservationList);
    }

    @Test
    void getReservationsByBookID_GivenBookID_ReturnReservationList() {
        reservationList.add(reservation);
        Reservation reservation1 = new Reservation();
        reservation1.setBookID("1");
        reservationList.add(reservation1);

        when(repository.findAllByBookID("1")).thenReturn(reservationList);
        assertThat(service.getReservationsByBookID("1")).isEqualTo(reservationList);
        assertThat(reservationList.size()).isEqualTo(2);
    }

    @Test
    void getReservationByUserEmail_GivenUserEmail_ReturnReservation() {
        when(repository.findAllByUserEmailIs("test@gmail.com")).thenReturn(reservationList);
        assertThat(service.getReservationByUserEmail("test@gmail.com")).isEqualTo(reservationList);
    }

    @Test
    void createReservation_GivenTooManyReservation_ReturnException() {
        Reservation reservation1 = new Reservation();
        reservationList.add(reservation1);
        Reservation reservation2 = new Reservation();
        reservationList.add(reservation2);

        when(repository.findAllByBookID("1")).thenReturn(reservationList);
        ReservationLimitException reservationLimitException = assertThrows(ReservationLimitException.class, ()-> service.createReservation(rDTO));
        assertThat(reservationLimitException.getMessage()).isEqualTo("Aucune réservation supplémentaire n'est disponible pour cet ouvrage.");
    }

    @Test
    void createReservation_GivenUserDoubleReservation_ReturnException() {
        reservationList.add(reservation);
        when(repository.findAllByBookID("1")).thenReturn(reservationList);
        ReservationLimitException reservationLimitException = assertThrows(ReservationLimitException.class, ()-> service.createReservation(rDTO));
        assertThat(reservationLimitException.getMessage()).isEqualTo("Vous ne pouvez réserver qu'un exemplaire de cet ouvrage");
    }

    @Test
    void createReservation_GivenAlreadyBorrowBookReservation_ReturnException()  {
        List<Book> bookList = new ArrayList<>();
        bookList.add(bookAvaible);
        borrow = Borrow.builder().id((long) 1).bookID("1").dateStart(new Date()).dateEnd(new Date()).dateExtend(new Date()).isExtend(false).userID("1").build();

        when(repository.findAllByBookID("1")).thenReturn(reservationList);
        when(userDabataseConnect.getUserFromDBByEmail(rDTO.getUserEmail())).thenReturn(user);
        when(borrowDatabaseConnect.getBorrowFromDBByBookID(rDTO.getBookID())).thenReturn(borrow);
        when(bookRepository.findByTitle("testBook")).thenReturn(bookList);

        ReservationLimitException reservationLimitException = assertThrows(ReservationLimitException.class, ()-> service.createReservation(rDTO));
        assertThat(reservationLimitException.getMessage()).isEqualTo("Vous ne pouvez pas réserver un livre que vous êtes actuellement entrain d'emprunter");
    }

    @Test
    void createReservation_GivenEmptyListAndAvaibleBook_ReturnReservation() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(bookAvaible);
        borrow = Borrow.builder().id(null).bookID(null).dateStart(null).dateEnd(null).dateExtend(null).isExtend(null).userID(null).build();

        when(mapper.reservatioDtoToReservation(rDTO)).thenReturn(reservation);
        when(repository.findAllByBookID("1")).thenReturn(reservationList);
        when(bookRepository.getOne((long) 1)).thenReturn(bookAvaible);
        when(bookRepository.findByTitle("testBook")).thenReturn(bookList);
        when(userDabataseConnect.getUserFromDBByEmail(rDTO.getUserEmail())).thenReturn(user);
        when(borrowDatabaseConnect.getBorrowFromDBByBookID(rDTO.getBookID())).thenReturn(borrow);

        service.createReservation(rDTO);

        Date today = new Date();
        verify(emailService, times(1)).createEmailInformations(reservation);
        verify(emailService, times(1)).sendEmailReservation(email);


        String todayDate = formater.format(today);
        String reservationDate = formater.format(reservation.getDate());

        assertThat(reservationDate).isEqualTo(todayDate);
        assertThat(reservation.getReservationPosition()).isEqualTo(1);
    }

    @Test
    void createReservation_GivenEmptyListAndNotAvaibleBook_ReturnReservation() {
        List<Book> bookList = new ArrayList<>();
        bookAvaible.setAvaible(false);
        bookList.add(bookAvaible);
        User fakeUser = new User();
        borrow = Borrow.builder().id((long) 1).bookID("1").dateStart(null).dateEnd(new Date()).dateExtend(null).isExtend(false).userID("1").build();

        when(mapper.reservatioDtoToReservation(rDTO)).thenReturn(reservation);
        when(repository.findAllByBookID("1")).thenReturn(reservationList);
        when(bookRepository.getOne((long) 1)).thenReturn(bookAvaible);
        when(bookRepository.findByTitle("testBook")).thenReturn(bookList);
        when(userDabataseConnect.getUserFromDBByEmail(rDTO.getUserEmail())).thenReturn(fakeUser);
        when(borrowDatabaseConnect.getBorrowFromDBByBookID(rDTO.getBookID())).thenReturn(borrow);

        service.createReservation(rDTO);

        String bookReturn = formater.format(reservation.getBookReturn());
        String borrowDateEnd = formater.format(borrow.getDateEnd());


        assertThat(bookReturn).isEqualTo(borrowDateEnd);
        assertThat(reservation.getReservationPosition()).isEqualTo(1);
    }

    @Test
    void createReservation_GivenList_ReturnReservation() {
        reservationList.add(reservation);
        List<Book> bookList = new ArrayList<>();
        bookAvaible.setAvaible(false);
        bookList.add(bookAvaible);
        borrow = Borrow.builder().id((long) 1).bookID("1").dateStart(null).dateEnd(new Date()).dateExtend(null).isExtend(false).userID("1").build();

        when(mapper.reservatioDtoToReservation(rDTO2)).thenReturn(reservation2);
        when(repository.findAllByBookID("1")).thenReturn(reservationList);
        when(bookRepository.getOne((long) 1)).thenReturn(bookAvaible);
        when(bookRepository.findByTitle("testBook")).thenReturn(bookList);
        when(userDabataseConnect.getUserFromDBByEmail(rDTO2.getUserEmail())).thenReturn(user2);
        when(borrowDatabaseConnect.getBorrowFromDBByBookID(rDTO2.getBookID())).thenReturn(borrow);


        service.createReservation(rDTO2);


        String bookDate = formater.format(borrow.getDateEnd());
        String borrowDate = formater.format(reservation2.getBookReturn());

        assertThat(bookDate).isEqualTo(borrowDate);
        assertThat(reservation2.getReservationPosition()).isEqualTo(2);
    }

    @Test
    void updateReservation_GivenWrongID_ReturnError() {
        when(repository.findById((long)1)).thenReturn(java.util.Optional.empty());
        ReservationNotFoundException reservationNotFoundException = assertThrows(ReservationNotFoundException.class, () -> service.updateReservation(rDTO));
        assertThat(reservationNotFoundException.getMessage()).isEqualTo("La réservation de ce livre n'existe pas ou elle est introuvable");
    }

    @Test
    void deleteReservation() {
       assertThat(service.deleteReservation((long)1)).isEqualTo(reservation.getId());
    }
}
