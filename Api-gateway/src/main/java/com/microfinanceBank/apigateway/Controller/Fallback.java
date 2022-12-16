package com.microfinanceBank.apigateway.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class Fallback {

    @RequestMapping("/customerFallBack")
    public Mono<ResponseEntity<String>> customerServiceFallback(){

        return Mono.just(new ResponseEntity<>("Customer Service is taking too long too respond or it is down. Please try again ", HttpStatus.SERVICE_UNAVAILABLE));
    }

    @RequestMapping("/transactionFallBack")
    public Mono<ResponseEntity<String>> transactionServiceFallback(){
        return Mono.just(new ResponseEntity<>("Transaction Service is taking too long too respond or it is down. Please try again ",HttpStatus.SERVICE_UNAVAILABLE));
    }

    @RequestMapping("/employeeFallBack")
    public Mono<ResponseEntity<String>> userServiceFallback(){
        return Mono.just(new ResponseEntity<>("Employee Service is taking too long too respond or it is down. Please try again ", HttpStatus.SERVICE_UNAVAILABLE));
    }

    @RequestMapping("/issueFallBack")
    public Mono<ResponseEntity<String>> issueServiceFallback(){
        return Mono.just(new ResponseEntity<>("Issue Service is taking too long too respond or it is down. Please try again ", HttpStatus.SERVICE_UNAVAILABLE));
    }
    @RequestMapping("/loanFallBack")
    public Mono<ResponseEntity<String>> loanServiceFallback(){
        return Mono.just(new ResponseEntity<>("Loan Service is taking too long too respond or it is down. Please try again ", HttpStatus.SERVICE_UNAVAILABLE));
    }
}
