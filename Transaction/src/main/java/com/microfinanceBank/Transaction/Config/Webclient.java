package com.microfinanceBank.Transaction.Config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class Webclient {

    @Profile("dev")
    public static class Dev {
        @Bean
        @LoadBalanced
        public WebClient.Builder webClient() {
            return WebClient.builder();
        }
    }

    @Profile("prod")
    public static class Prod {
        @Bean
        public WebClient.Builder webClient() {
            return WebClient.builder();
        }
    }



}
