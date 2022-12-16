package com.microfinanceBank.Loan.enums;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microfinanceBank.Loan.dto.LoanRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.stream.IntStream;

public class opop {

    public static void main(String[] args) throws JsonProcessingException {


        var parallelStreamResult=parallelStream();
        System.out.println("Time taken stream parallel: " + parallelStreamResult);

        System.out.println("================================");


        var streamResult=stream();
        System.out.println("Time taken ordinary stream : " + streamResult);
        System.out.println("================================");



    }

    public static long parallelStream(){
        long start=System.currentTimeMillis();
        IntStream.range(0,100).parallel().forEach(x->{
            System.out.println("parallel Thread name for number "+x +" with  thread "+Thread.currentThread().getName());
        });

         long end = System.currentTimeMillis();

         return  end-start;

    }
    public static long stream(){
        long start=System.currentTimeMillis();
        IntStream.range(0,100).forEach(x->{
            System.out.println("ordinary stream Thread name for number "+x +" with  thread "+Thread.currentThread().getName());
        });

         long end = System.currentTimeMillis();

         return  end-start;

    }

}
