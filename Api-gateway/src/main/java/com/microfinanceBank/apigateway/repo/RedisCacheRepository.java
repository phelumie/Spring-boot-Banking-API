package com.microfinanceBank.apigateway.repo;

import com.microfinanceBank.apigateway.model.CachedRequest;
import com.microfinanceBank.apigateway.model.CachedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
@Repository
@Profile("redisCache")
public class RedisCacheRepository {

    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, CachedResponse> cache;

    public RedisCacheRepository(ReactiveRedisConnectionFactory factory, ReactiveRedisOperations<String, CachedResponse> cache) {
        this.factory = factory;
        this.cache = cache;
    }


    public Flux<CachedResponse> get(CachedRequest cachedRequest) {
        return cache.keys(cachedRequest.toString())
                .flatMap(cache.opsForValue()::get);
    }

    public Flux<Boolean> put(CachedRequest cacheKey, CachedResponse cachedResponse) {
        return factory.getReactiveConnection().serverCommands().bgSave().thenMany(
                Flux.just(cachedResponse)
                        .flatMap(data -> cache.opsForValue().set(cacheKey.toString(), cachedResponse, Duration.ofMinutes(30)))
        );

    }

}
