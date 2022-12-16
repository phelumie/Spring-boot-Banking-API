package com.microfinanceBank.Loan.exceptions.handler;

import com.microfinanceBank.Loan.exceptions.UnderAgeException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(UnderAgeException.class)
    public ResponseEntity<Object> underAgeException(UnderAgeException underAgeException){
        HttpStatus status=HttpStatus.EXPECTATION_FAILED;
        ApiExceptionMessage exception=new ApiExceptionMessage(
                underAgeException.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of("UTC+1")));
        return new ResponseEntity<>(exception,status);
    }

    @Data
    class ApiExceptionMessage{
        private final String message;
        private final HttpStatus status;
        private final ZonedDateTime timeStamp;

        ApiExceptionMessage(String message, HttpStatus status, ZonedDateTime timeStamp) {
            this.message = message;
            this.status = status;
            this.timeStamp = timeStamp;
        }
    }
}
