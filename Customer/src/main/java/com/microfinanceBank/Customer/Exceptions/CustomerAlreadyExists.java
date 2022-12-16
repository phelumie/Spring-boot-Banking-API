package com.microfinanceBank.Customer.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CustomerAlreadyExists extends RuntimeException {

    public CustomerAlreadyExists(String message) {
        super(message);
    }

}
