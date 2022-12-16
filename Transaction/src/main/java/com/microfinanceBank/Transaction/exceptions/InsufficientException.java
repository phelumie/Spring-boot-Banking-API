package com.microfinanceBank.Transaction.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientException extends RuntimeException {
    public InsufficientException(String message) {
        super(message);
    }


}
