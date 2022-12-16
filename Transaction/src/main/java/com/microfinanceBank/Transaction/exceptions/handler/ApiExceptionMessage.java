package com.microfinanceBank.Transaction.exceptions.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiExceptionMessage {
    public  String message;
    public HttpStatus status;
    public ZonedDateTime timeStamp;


}
