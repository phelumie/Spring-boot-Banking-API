package com.microfinanceBank.Employee.exceptions.handler;

import com.microfinanceBank.Employee.exceptions.EmployeeAlreadyExists;
import com.microfinanceBank.Employee.exceptions.NoEmployeeExceptions;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(EmployeeAlreadyExists.class)
    public ResponseEntity<Object> employeeAlreadyExists(EmployeeAlreadyExists employeeAlreadyExists){
        HttpStatus status=HttpStatus.CONFLICT;
        ApiExceptionMessage exception=new ApiExceptionMessage(
                employeeAlreadyExists.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of("UTC+1")));
        return new ResponseEntity<>(exception,status);
    }

    @ExceptionHandler(NoEmployeeExceptions.class)
    public ResponseEntity<Object> noEmployeeExceptions(NoEmployeeExceptions noEmployeeExceptions){
        HttpStatus status=HttpStatus.NOT_FOUND;
        ApiExceptionMessage exception=new ApiExceptionMessage(
                noEmployeeExceptions.getMessage(),
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
