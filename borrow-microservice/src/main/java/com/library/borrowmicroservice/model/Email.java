package com.library.borrowmicroservice.model;

import lombok.Data;

import java.util.Date;

@Data
public class Email {

    //User
    private String firstName;
    private String lastName;
    private String emailUser;

    // Book
    private String bookTitle;

    // Borrow
    private Boolean isExtend;
    private Date dateEnd;
    private Date dateExtend;
}
