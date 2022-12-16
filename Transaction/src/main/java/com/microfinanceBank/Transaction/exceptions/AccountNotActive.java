package com.microfinanceBank.Transaction.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccountNotActive extends RuntimeException{

    public AccountNotActive(String message) {
        super(message);
    }
}
