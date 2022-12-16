package com.microfinanceBank.Transaction.controller;


import com.microfinanceBank.commondto.transaction.LocationDto;
import com.microfinanceBank.commondto.transaction.TransactionDto;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class TransactionControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private Flyway flyway;
    private String baseUrl="http://localhost";
    @Autowired
    private WebTestClient webClient;

    private static String token;



    @Value("${token}")
    public  void setTokenFromProperties(String value){
        var token=value.split(" ")[1];
        this.token=token;
    }


    @BeforeAll
    public static void init(){

    }
    @BeforeEach
    public void cleanUp(){
        flyway.clean();
        flyway.migrate();
    }
    @BeforeEach
    public void setup(){
        baseUrl= baseUrl.concat(":").concat(port+"").concat("/api");
    }




    @Test
    void deposit() {
        var dto=TransactionDto.builder()

                .locationDto(new LocationDto("fcmb","Lagos"))
                .amount(BigDecimal.valueOf(1000))
                .description("deposit")
                .build();

        webClient.post()
                .uri(baseUrl.concat("/deposit"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .exchange()
                .expectStatus().isBadRequest();


        dto.setSourceAccount(1234566789L);
        dto.setAmount(BigDecimal.valueOf(-100));
        webClient.post()
                .uri(baseUrl.concat("/deposit"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .exchange()
                .expectStatus().isBadRequest();

        dto.setAmount(BigDecimal.valueOf(1000));
        webClient.post()
                .uri(baseUrl.concat("/deposit"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .exchange()
                .expectStatus().isAccepted();

    }
}