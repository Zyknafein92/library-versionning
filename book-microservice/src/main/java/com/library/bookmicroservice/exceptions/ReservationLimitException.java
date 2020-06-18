package com.library.bookmicroservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class ReservationLimitException extends RuntimeException{

    public ReservationLimitException(String message) {
        super(message);
    }
}
