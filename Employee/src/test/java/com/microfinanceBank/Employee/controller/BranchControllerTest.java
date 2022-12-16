package com.microfinanceBank.Employee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microfinanceBank.Employee.dto.AddressDto;
import com.microfinanceBank.Employee.dto.BranchDto;
import com.microfinanceBank.Employee.repository.BranchRepository;
import com.microfinanceBank.Employee.repository.EmployeeRepository;
import com.microfinanceBank.Employee.service.BranchService;
import com.microfinanceBank.Employee.service.EmployeeService;
import com.microfinanceBank.Employee.service.KeycloakProvider;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BranchControllerTest {



    @LocalServerPort
    private int port;

    @Value("${Keycloak.realm}")
    private String realm;
    @Autowired
    private Flyway flyway;
    private String baseUrl="http://localhost";
    @Autowired
    private static  RestTemplate restTemplate;
    @Autowired
    private BranchService branchService;
    private ObjectMapper objectMapper;
    @Autowired
    private BranchRepository branchRepository;
    private static HttpHeaders headers;
    private static String token="Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJGdF95bWVETmxmYkVjazVfTXZLOW9mQzRzVmJqTnd2Zmw5SlM1YVVYbTY4In0.eyJleHAiOjE2NjM2ODI2NjUsImlhdCI6MTY2MzI1MDY2NSwianRpIjoiNjY0NzI3MTAtM2E5MS00ZjM3LTk3ZDktNDY0OGJiMGIwOWQ4IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo5MDkwL3JlYWxtcy9TcHJpbmdib290LWJhbmstbWljcm9zZXJ2aWNlcyIsInN1YiI6IjZmMmEyODZmLTMwMTgtNDdhOS05ZjM3LWY0OGFkZWFlYzg5YiIsInR5cCI6IkJlYXJlciIsImF6cCI6ImZyb250LWVuZCIsInNlc3Npb25fc3RhdGUiOiIyOWQ2OGIyYi04MGNkLTQyMzAtOWNiYi1kYTgyOGFlYzBiODkiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbIioiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiYWRtaW4iLCJkZWZhdWx0LXJvbGVzLXNwcmluZ2Jvb3QtYmFuay1taWNyb3NlcnZpY2VzIiwiaHIiLCJ1bWFfYXV0aG9yaXphdGlvbiIsImVtcGxveWVlIl19LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIiwic2lkIjoiMjlkNjhiMmItODBjZC00MjMwLTljYmItZGE4MjhhZWMwYjg5IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6ImFqaXNlZ2lyaXMxQGdtYWlsLmNvIiwiZW1haWwiOiJhamlzZWdpcmlzMUBnbWFpbC5jbyJ9.isGP_NqLc76FW6Sx_qbRC16rYSPGGaJwud0d2CG93TO11W3xibqnVo2e_lX6HsvnLAbQ1Z0rytIoIY4DWxGKLquno5QW6Rt8mIsKCYe7baIvzDtOwCSuCDf1ei2jy6z20NJnIY9ixVxlQJhwzW0PrmGU1uYK2XlyH2LiJb7syhAqGjrdGHSRodEuVY4JB0R5Tv_ZXIxzwJl0h58pd3skHUsyuRTr2zn1I7k12L8oo64uRLIrfnjDm_60qefT9IegKs3PJMSiKTqWHQ90bjrIqBAZArq8diViA-97gSSjeCN8O0Ugk18S5zXPZMnq9MgvhNq4FCTZ_HUOIJo3B_VQ1g";

    @BeforeAll
    public static void init(){
        restTemplate= new RestTemplate();
        headers=new HttpHeaders();

        headers.add(HttpHeaders.AUTHORIZATION,token);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }
    @BeforeEach
    public void cleanUp(){
        flyway.clean();
        flyway.migrate();
    }
    @BeforeEach
    public void setup(){
        baseUrl= baseUrl.concat(":").concat(port+"").concat("/api");
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }




    @Test
    void createBranch() throws JsonProcessingException {
        var branch=createBranchDto();

var a=objectMapper.writeValueAsString(branch);
        var request = new HttpEntity<>(a,headers);


        ResponseEntity<BranchDto> response = restTemplate
                .exchange(baseUrl.concat("/branch/"), HttpMethod.POST ,request, BranchDto.class);

        var body=response.getBody();

        assertNotNull(body);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(branch.getAddress().getPostalCode(),body.getAddress().getPostalCode());
        assertEquals(1,branchRepository.findAll().size());
        assertEquals(1,branchService.getAll().size());

    }

    @Test
    void getAllBranch() {
        var branch=branchService.createBranch(createBranchDto());
        var branch2=branchService.createBranch(createBranchDto());

        var request = new HttpEntity<>(headers);

        ResponseEntity<BranchDto[]> response = restTemplate
                .exchange(baseUrl.concat("/all-branch/"), HttpMethod.GET ,request, BranchDto[].class);

        var body=response.getBody();
        assertNotNull(body);
        assertEquals(2,branchRepository.findAll().size());
        assertEquals(2,branchService.getAll().size());
    }


    private BranchDto createBranchDto() {
        var branchAddress= new AddressDto();
        branchAddress.setCity("Lekki");
        branchAddress.setState("Lagos");
        branchAddress.setPostalCode("12107");
        branchAddress.setStreet("Lekki Phase 1");
        branchAddress.setCountry("Nigeria");

        var branch=new BranchDto();

        branch.setAddress(branchAddress);
        return  branch;
    }
}