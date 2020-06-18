package com.library.bookmicroservice.services.util;

import com.library.bookmicroservice.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.text.SimpleDateFormat;

//public class ReservationEmail {
//
//    @Autowired
//    JavaMailSender javaMailSender;
//
//    public void sendEmailReservation(Email email) {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//
//        message.setTo(email.getEmailUser());
//        message.setSubject("Emprunt arrivé a échèance");
//        message.setText("Nous vous informations que le livre " + email.getBookTitle() + " est disponible." +
//                "Vous disposez de 48h pour venir le récupérer sans quoi il sera remis à disposition d'un autre utilisateur.\n\n " +
//                "Bien Cordialement,") ;
//        javaMailSender.send(message);
//    }
//}
