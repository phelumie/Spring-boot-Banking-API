package com.microfinanceBank.apigateway.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Profile("redisCache")
public class RateLimiterServiceImpl implements RateLimiterService {

    /**
     * This method is to return the current number
     * of api calls made by this user from cache
     * @param userId - keycloak id
     * @return String - number of calls made by this user
     */

    private final ReactiveRedisTemplate<String,String> redisTemplate;
    private final ReactiveValueOperations<String,String> ops;

    public RateLimiterServiceImpl(ReactiveRedisTemplate<String, String> redisTemplate, ReactiveValueOperations<String, String> ops) {
        this.redisTemplate = redisTemplate;
        this.ops = ops;
        ops=redisTemplate.opsForValue();
    }


    @Override
    public Mono<Integer> getApiHitCount(String id) {
        return ops.get(id).map(digit->Integer.valueOf(digit));

    }


    @Override
    public Mono<Long> incrementApiHitCount(String id) {
        return ops.increment(id);

    }

    @Override
    public Mono<Boolean> put(String id) {
        return ops.set(id,"1", Duration.ofSeconds(1));

    }

    /**
     * This method is to increment the number of api
     * calls made by this user in cache
     * @param userId - keycloak id
     * @return void
     */
//    @Override
//    public void incrementApiHitCount(String userId) {
//        template.
//                opsForValue().
//                increment("customer" + "::" + userId);
//    }
}
