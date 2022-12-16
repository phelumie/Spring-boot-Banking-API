package com.microfinanceBank.Issues.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoIssueFound extends RuntimeException{
    public NoIssueFound(String message) {
        super(message);
    }

}
