package com.microfinanceBank.apigateway.filter;

import com.microfinanceBank.apigateway.model.CachedRequest;
import com.microfinanceBank.apigateway.model.CachedResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.microfinanceBank.apigateway.utils.ConstantUtil.FILTER_PATTERNS;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang.StringUtils.EMPTY;

/*
This is a post filter (to perform cache and xss prevention on responses)

 */
@Slf4j
@Component
@Profile("localCache")
public class LocalCacheAndXssFilter implements GlobalFilter, Ordered {
    private final CacheManager cacheManager;
    private String swaggerUrl="/v3/api-docs";
    Map<String,String> allowedUrls;
    @org.springframework.beans.factory.annotation.Value("${keycloakIssuerUri}")
    private  String issuerUri;

    public LocalCacheAndXssFilter(CacheManager cacheManager) {
        System.out.println("qqqqqqqqqqqqqqqqqqqqqq");
        this.cacheManager = cacheManager;
        if (allowedUrls==null)
            this.allowedUrls=new HashMap<>(3);
        allowedUrls.put("/customer/api/customers","ADMIN");
        allowedUrls.put("/customer/api/accounts", "ADMIN");
        allowedUrls.put("/customer/api/account?accountNumber","ADMIN");



    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("ccccccccccccccccccc");
 allowedUrls.entrySet().stream().forEach(System.out::println);
        var path=exchange.getRequest().getPath().value();

        Set<String> userAuthorities =userAuthorities(exchange);
        boolean isGetMethod=exchange.getRequest().getMethodValue().equals("GET");

        final var cache = cacheManager.getCache("MyCache");
        final var allowedRole=allowedUrls.get(path);
        final var cachedRequest = getCachedRequest(exchange.getRequest());

            if (isGetMethod && nonNull(cache.get(cachedRequest))) {
                if (!userAuthorities.contains(allowedRole)){
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }
            log.info("Return cached response for request: {}", cachedRequest);
            final var cachedResponse = cache.get(cachedRequest, CachedResponse.class);
            final var serverHttpResponse = exchange.getResponse();
                MultiValueMap headerMap=new LinkedMultiValueMap();
                headerMap.set("cached-response","true");
                cachedResponse.getHeaders().entrySet()
                        .stream().forEach(data->headerMap.set(data.getKey(),data.getValue()));
            serverHttpResponse.setStatusCode(cachedResponse.getHttpStatus());
            serverHttpResponse.getHeaders().addAll(headerMap);
            final var buffer = exchange.getResponse().bufferFactory().wrap(cachedResponse.getBody());
            return exchange.getResponse().writeWith(Flux.just(buffer));
            }


        final var mutatedHttpResponse = getServerHttpResponse(exchange, cache, cachedRequest,path);
            mutatedHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return chain.filter(exchange.mutate().response(mutatedHttpResponse).build());
    }


    private ServerHttpResponseDecorator getServerHttpResponse(ServerWebExchange exchange, Cache cache, CachedRequest cachedRequest,String path) {
        final var originalResponse = exchange.getResponse();
        final var dataBufferFactory = originalResponse.bufferFactory();
        return new ServerHttpResponseDecorator(originalResponse) {
            @NonNull
            @Override
            public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {

                if (body instanceof Flux) {
                    final var flux = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(flux.buffer()
                            .map(dataBuffers -> {
                        final var outputStream = new ByteArrayOutputStream();
                        dataBuffers.forEach(dataBuffer -> {

                            var responseContent = new byte[dataBuffer.readableByteCount()];

                            dataBuffer.read(responseContent);

                            try {
                                outputStream.write(responseContent);
                            } catch (IOException e) {
                            }

                        });

                        //xss filter


                            try {
                                var unfiltered = outputStream.toByteArray();
                                outputStream.reset();
                                var filtered=xssReplaceDangerousContents(unfiltered);
                                outputStream.writeBytes(filtered);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }


                                boolean isGetMethod=exchange.getRequest().getMethodValue().equals("GET");

                                if (Objects.requireNonNull(getStatusCode()).is2xxSuccessful() &&isGetMethod &&allowedUrls.containsKey(path)) {
                                final var cachedResponse = new CachedResponse(getStatusCode(), getHeaders().toSingleValueMap(), outputStream.toByteArray());
                                log.debug("Request {} Cached response {}", path, new String(cachedResponse.getBody(), UTF_8));
                                var cacheKey=getCachedRequest(exchange.getRequest());
                                cache.put(cacheKey, cachedResponse);

                        }

                        return dataBufferFactory.wrap(outputStream.toString().getBytes());
                    }));
                }

                return super.writeWith(body).log();
            }
        };
    }

    @Override
    public int getOrder() {
        return -2;
    }

    private CachedRequest getCachedRequest(ServerHttpRequest request) {
        return CachedRequest.builder()
                .method(request.getMethod())
                .path(request.getPath())
                .queryParams(request.getQueryParams())
                .build();
    }



    private byte[] xssReplaceDangerousContents(byte[] bytes) throws IOException {

        var data=new String(bytes);
        if (data != null) {
            for (Pattern pattern : FILTER_PATTERNS)
                data = pattern.matcher(data).replaceAll(EMPTY);
        }

        return data.getBytes();
    }
    private Set<String> userAuthorities(ServerWebExchange exchange){
        var path=exchange.getRequest().getPath().value();
        if (path.startsWith(swaggerUrl) || path.endsWith(swaggerUrl) || path.contains("actuator") )
            return null;
        var token=exchange.getRequest().getHeaders().get("Authorization").get(0).split(" ")[1];
        var jwt=JwtDecoders.fromIssuerLocation(issuerUri).decode(token);
        var userAuthorities=convert(jwt);
        return userAuthorities;
    }


    public Set<String> convert(Jwt jwt) {
        final Map<String, List<String>> realmAccess = (Map<String, List<String>>) jwt.getClaims().get("realm_access");
        return realmAccess.get("roles")
                .stream()
                .collect(Collectors.toSet());
    }

}