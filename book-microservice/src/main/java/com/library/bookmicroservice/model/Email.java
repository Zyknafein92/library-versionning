package com.library.bookmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Email {

    // User
    private String emailUser;

    // Book
    private String bookTitle;
}
