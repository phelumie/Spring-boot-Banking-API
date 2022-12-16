package com.microfinanceBank.Employee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmployeeAlreadyExists extends RuntimeException {

    public EmployeeAlreadyExists(String message) {
        super(message);
    }

}
