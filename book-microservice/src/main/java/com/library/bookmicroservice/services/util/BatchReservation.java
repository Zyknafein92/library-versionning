package com.library.bookmicroservice.services.util;

import com.library.bookmicroservice.model.Book;
import com.library.bookmicroservice.model.Email;
import com.library.bookmicroservice.model.Reservation;
import com.library.bookmicroservice.repository.ReservationRepository;
import com.library.bookmicroservice.services.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class BatchReservation {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservationService reservationService;

    List<Reservation> reservationList = new ArrayList<>();


    //@Scheduled(cron= "0 0 0 * * *") //tous les jours à minuit.
    @Scheduled(fixedDelay = 120000) // toutes les 2 minutes pour démo.
    public void checkReservationDate() {

//        reservationList = initReservationList();
//        for (Reservation reservation : reservationList) {
//            Calendar rDate = Calendar.getInstance();
//            Calendar today = Calendar.getInstance();
//
//            rDate.setTime(reservation.getDate());
//            rDate.add(Calendar.DATE, 2);
//
//            if (rDate.after()) {
////                reservationService.deleteReservation(reservation.getId());
////            }
//
//        }
//
}

    //
//    private Email createEmailInformations (Book book, Reservation reservation) {
//
//        Email emailToSend = new Email();
//
//        emailToSend.setEmailUser(reservation.getUserEmail());
//        emailToSend.setBookTitle(book.getTitle());
//
//        return emailToSend;
//    }
//
    private List<Reservation> initReservationList() {
        return reservationList = reservationRepository.findAll();
    }

}
