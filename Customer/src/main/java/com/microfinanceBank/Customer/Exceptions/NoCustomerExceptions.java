package com.microfinanceBank.Customer.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoCustomerExceptions extends RuntimeException {

    public NoCustomerExceptions(String message) {
        super(message);
    }

}
