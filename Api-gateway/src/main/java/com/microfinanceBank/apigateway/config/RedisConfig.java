package com.microfinanceBank.apigateway.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microfinanceBank.apigateway.model.CachedResponse;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.netty.handler.codec.HeadersUtils;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Configuration
@Profile("redisCache")
@RequiredArgsConstructor
public class RedisConfig {


    @Bean
    ReactiveRedisOperations<String, CachedResponse> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<CachedResponse> serializer = new Jackson2JsonRedisSerializer<>(CachedResponse.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, CachedResponse> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, CachedResponse> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
//    @Bean
//    public LettuceClientConfigurationBuilderCustomizer clientConfigurationBuilderCustomizer(){
//        SocketOptions socketOptions=SocketOptions.builder().connectTimeout(Duration.ofMillis(1000)).build();
//        ClientOptions clientOptions=ClientOptions.builder().socketOptions(socketOptions).build();
//        return clientConfigurationBuilder -> clientConfigurationBuilder.clientOptions(clientOptions);
//    }
//    @Bean
//    @Primary
//    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
//        return new LettuceConnectionFactory(prop.getRedisHost(), 6379);
//    }
//@Bean(name = "userKeyResolver")
//KeyResolver userKeyResolver() {
//    return exchange -> Mono.just(exchange.getRequest().getHeaders().get("X-Keycloak-Id").get(0));
//}
//    @Bean
//    public ObjectMapper objectMapper(){
//        ObjectMapper mapper=new ObjectMapper();
//        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        mapper.registerModule(new JavaTimeModule());
////        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//        mapper.registerSubtypes(HttpHeaders.class, LinkedMultiValueMap.class);
//        mapper.disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
//        return mapper;
//    }
//

//    @Bean
//    public RouteLocator myRoutes(RouteLocatorBuilder routeLocatorBuilder)
//    {
//        return routeLocatorBuilder.routes()
//                .route(p ->p
//                        .path("/customer/**")
//                        .filters(f->f.requestRateLimiter().configure(c->c.setRateLimiter(redisRateLimiter()))
//                                .stripPrefix(1))
//
//                        .uri("lb://customer"))
//                .build();
//    }
//
//    @Bean
//    public RedisRateLimiter redisRateLimiter()
//    {
//        return new RedisRateLimiter(1,2);
//    }

}

