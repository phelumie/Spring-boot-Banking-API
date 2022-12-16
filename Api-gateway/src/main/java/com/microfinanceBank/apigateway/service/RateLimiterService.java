package com.microfinanceBank.apigateway.service;

import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

public interface RateLimiterService {
     Mono<Integer> getApiHitCount(String id);

     Mono<Long> incrementApiHitCount(String id);

     Mono<Boolean> put(String id);
//     String getApiHitCount(String userId);


//     void incrementApiHitCount(String userId);


}