package com.microfinanceBank.Loan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microfinanceBank.Loan.dto.LoanRequest;
import com.microfinanceBank.Loan.dto.LoanRequestResponse;
import com.microfinanceBank.Loan.entity.BorrowerDetails;
import com.microfinanceBank.Loan.entity.LoanOffer;
import com.microfinanceBank.Loan.enums.AccountType;
import com.microfinanceBank.Loan.enums.LoanStatus;
import com.microfinanceBank.Loan.enums.MaritalStatus;
import com.microfinanceBank.Loan.repo.LoanRepository;
import com.microfinanceBank.Loan.service.LoanService;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoanControllerTest {


    @LocalServerPort
    private int port;
    @Autowired
    private Flyway flyway;
    private String baseUrl="http://localhost";

    @Autowired
    private LoanService loanService;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private static RestTemplate restTemplate;
    private static HttpHeaders headers;

    private static String token;



    @BeforeAll
    public static void init(){
        restTemplate= new RestTemplate();
        headers=new HttpHeaders();

    }
    @BeforeEach
    public void cleanUp(){
        flyway.clean();
        flyway.migrate();
    }

    @BeforeEach
    public void setup(){
        baseUrl= baseUrl.concat(":").concat(port+"").concat("/api");
        headers.add(HttpHeaders.AUTHORIZATION,token);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    @Value("${token}")
    public  void setTokenFromProperties(String value){
        token=value;
    }


    @Test
    void bankLoanRequest() {
        var loanRequest=generateLoanRequest();

        var request = new HttpEntity<>(loanRequest,headers);

        var response=restTemplate.exchange(baseUrl.concat("/bank-loan"), HttpMethod.POST,request, LoanRequestResponse.class);

        assertDoesNotThrow(()->response);
        assertNotNull(response.getBody().getApplicationDate());
        assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());
        assertEquals(loanRequest.getPrincipalLoanAmount(),request.getBody().getPrincipalLoanAmount());
        assertEquals(loanRequest.getAccountType(),request.getBody().getAccountType());
        assertEquals(loanRequest.getBranchId(),request.getBody().getBranchId());


    }

    @Test
    void approveLoans() {
        var loanRequest=generateLoanRequest();

        var requestLoan=loanService.loanRequest(loanRequest);
        var loanId=requestLoan.getLoanId();

        var request = new HttpEntity<>(headers);
        var response=restTemplate.exchange(baseUrl.concat("/approve-loan?loan-id=").concat(loanId), HttpMethod.PUT,request,Void.class);

        assertDoesNotThrow(()->response);
        assertEquals(HttpStatus.OK,response.getStatusCode());

        assertEquals(LoanStatus.ACCEPTED,loanRepository.findById(loanId).get().getStatus());

    }
    private LoanRequest generateLoanRequest() {

        var loanOffer=new LoanOffer();
        loanOffer.setId(1);
        loanOffer.setLoanName("Personal loan");
        loanOffer.setLoanRange(String.valueOf(5000000));
        loanOffer.setInterest(5);
        loanOffer.setLatePaymentInterest(2.5);

        var borrowerDetails=new BorrowerDetails();
        borrowerDetails.setChildren(0);
        borrowerDetails.setEmail("borrower@gmail.com");
        borrowerDetails.setMaritalStatus(MaritalStatus.SINGLE);
        borrowerDetails.setDob(LocalDate.parse( "1999-01-01"));
        borrowerDetails.setOccupation("trader");
        borrowerDetails.setMobileNumber("08000000000");
        borrowerDetails.setMonthlyIncome(500000);

        var loanRequest= LoanRequest.builder()
                .loanOffer(loanOffer)
                .borrowerDetails(borrowerDetails)
                .haveAnExistingLoan(false)
                .principalLoanAmount(BigDecimal.valueOf(1000000))
                .description("clearing of goods")
                .accountType(AccountType.SAVINGS)
                .joinDate(LocalDate.now())
                .branchId(10L)
                .monthlyExpenses(250000)
                .numberOfPayments(5)
                .borrowerAccountNumber(123456789L).build();

        return loanRequest;


    }
}
