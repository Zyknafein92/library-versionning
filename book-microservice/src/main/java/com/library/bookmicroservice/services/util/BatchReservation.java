package com.library.bookmicroservice.services.util;

import com.library.bookmicroservice.model.Book;
import com.library.bookmicroservice.model.Email;
import com.library.bookmicroservice.model.Reservation;
import com.library.bookmicroservice.repository.ReservationRepository;
import com.library.bookmicroservice.services.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class BatchReservation {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservationService reservationService;
    @Autowired
    EmailService emailService;

    List<Reservation> reservationsOutDated = new ArrayList<>();


    //@Scheduled(cron= "0 0 0 * * *") //tous les jours à minuit.
    @Scheduled(fixedDelay = 120000) // toutes les 2 minutes pour démo.
    public void runCheckReservationOutDatedDate() {

        // Clean des 48h
        reservationsOutDated = reservationRepository.findOutDatedReservation();

        if(reservationsOutDated.size() > 0) {
            for (Reservation reservation: reservationsOutDated) {
                String bookID = reservation.getBookID();
                Email email = emailService.createEmailInformations(reservation);
                emailService.sendEmailCancelReservation(email);
                reservationRepository.deleteById(reservation.getId());

                //Attribution des nouvelles réservations
                reservationService.updateBookReservation(bookID);
            }
        }
    }

}
