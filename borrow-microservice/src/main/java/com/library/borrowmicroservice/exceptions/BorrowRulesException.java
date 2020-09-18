package com.library.borrowmicroservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BorrowRulesException extends RuntimeException {

    public BorrowRulesException(String message) {
        super(message);
    }
}
