package com.microfinanceBank.Transaction.exceptions.handler;

import com.microfinanceBank.Transaction.exceptions.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(AccountNotActive.class)
    public ResponseEntity<Object> accountNotActive(AccountNotActive accountNotActive){
        HttpStatus status=HttpStatus.FORBIDDEN;
        ApiExceptionMessage exception=new ApiExceptionMessage(
                accountNotActive.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of("UTC+1")));
        return new ResponseEntity<>(exception,status);
    }

    @ExceptionHandler(CustomerAlreadyExists.class)
    public ResponseEntity<Object> customerAlreadyExists(CustomerAlreadyExists customerAlreadyExists){
        HttpStatus status=HttpStatus.CONFLICT;
        ApiExceptionMessage exception=new ApiExceptionMessage(
                customerAlreadyExists.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of("UTC+1")));
        return new ResponseEntity<>(exception,status);
    }

    @ExceptionHandler(InsufficientException.class)
    public ResponseEntity<Object> insufficientException(InsufficientException insufficientException){
        HttpStatus status=HttpStatus.BAD_REQUEST;
        ApiExceptionMessage exception=new ApiExceptionMessage(
                insufficientException.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of("UTC+1")));
        return new ResponseEntity<>(exception,status);
    }

    @ExceptionHandler(NoCustomerExceptions.class)
    public ResponseEntity<Object> noCustomerExceptions(NoCustomerExceptions noCustomerExceptions){
        HttpStatus status=HttpStatus.NOT_FOUND;
        ApiExceptionMessage exception=new ApiExceptionMessage(
                noCustomerExceptions.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of("UTC+1")));
        return new ResponseEntity<>(exception,status);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<Object> noCustomerExceptions(ServiceUnavailableException serviceUnavailableException){
        HttpStatus status=HttpStatus.SERVICE_UNAVAILABLE;
        ApiExceptionMessage exception=new ApiExceptionMessage(
                serviceUnavailableException.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of("UTC+1")));
        return new ResponseEntity<>(exception,status);
    }



}
