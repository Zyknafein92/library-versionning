package com.library.bookmicroservice.services.util;

import com.library.bookmicroservice.model.Email;
import com.library.bookmicroservice.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Component
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    public Email createEmailInformations (Reservation reservation) {
        Email emailToSend = new Email();

        emailToSend.setEmailUser(reservation.getUserEmail());
        emailToSend.setBookTitle(reservation.getBookTitle());

        return emailToSend;
    }

    public void sendEmailReservation(Email email) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email.getEmailUser());
        message.setSubject("Votre livre est disponible");

        message.setText("Nous vous informons que le livre " + email.getBookTitle() + " est disponible. \n\n" +
                "Vous disposez de 48h pour venir le récupérer sans quoi il sera remis à disposition d'un autre utilisateur.\n\n " +
                "Bien Cordialement,") ;

        javaMailSender.send(message);
    }

    public void sendEmailCancelReservation(Email email) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email.getEmailUser());

        message.setSubject("Emprunt arrivé a échèance");

        message.setText("Nous vous informons que le réservation que vous aviez faite sur l'ouvrage  " + email.getBookTitle() + " est arrivée à son terme. \n\n " +
                "Etant donné que vous n'êtes pas venu dans les 48h qui ont suivi la réservation de l'ouvrage, celui-ci a été remis à disposition des autres utilisateurs.\n\n " +
                "Bien Cordialement,") ;

        javaMailSender.send(message);
    }

}

