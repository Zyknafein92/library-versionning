package com.library.borrowmicroservice.batch;

import com.library.borrowmicroservice.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class EmailConfig {

    @Autowired
    JavaMailSender javaMailSender;


    public void sendEmailwithoutExtension(Email email) {

        SimpleMailMessage message = new SimpleMailMessage();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        message.setTo(email.getEmailUser());
        message.setSubject("Emprunt arrivé a échèance");
        message.setText("A l'attention de " + email.getFirstName() + " " + email.getLastName() + ".\n\n  L'emprunt du livre \" " + email.getBookTitle() + " \" est arrivé à terme depuis le " + dateFormat.format(email.getDateEnd()) + ". Nous vous prions de bien vouloir le restituer le plus rapidement possible à sa bibliothèque d'origine ou de signaler le prolongement de votre emprunt.\n\n Bien Cordialement,") ;
        javaMailSender.send(message);
    }

    public void sendEmailwithExtension(Email email) {

        SimpleMailMessage message = new SimpleMailMessage();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        message.setTo(email.getEmailUser());
        message.setSubject("Emprunt arrivé a échèance");
        message.setText("A l'attention de " + email.getFirstName() + " " + email.getLastName() +".\n\n  L'emprunt du livre \" " + email.getBookTitle() + " \" est arrivé à terme depuis le " + dateFormat.format(email.getDateEnd()) + ". Nous vous prions de bien vouloir le restituer le plus rapidement possible à sa bibliothèque d'origine.\n\n Bien Cordialement,") ;
        javaMailSender.send(message);
    }
}


