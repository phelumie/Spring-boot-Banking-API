package com.microfinanceBank.Loan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UnderAgeException extends RuntimeException {

    public UnderAgeException(String message) {
        super(message);
    }
}
