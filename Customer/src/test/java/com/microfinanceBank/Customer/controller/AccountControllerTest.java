package com.microfinanceBank.Customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microfinanceBank.Customer.Config.KeycloakProvider;
import com.microfinanceBank.Customer.Exceptions.NoCustomerExceptions;
import com.microfinanceBank.Customer.dto.*;
import com.microfinanceBank.Customer.enums.AccountType;
import com.microfinanceBank.Customer.enums.Status;
import com.microfinanceBank.Customer.service.AccountService;
import com.microfinanceBank.Customer.service.CustomerService;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.rmi.UnexpectedException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {

    @Value("${Keycloak.realm}")
    private String realm;

    @LocalServerPort
    private int port;
    @Autowired
    private Flyway flyway;

    @Autowired
    private KeycloakProvider keycloak;
    private String baseUrl="http://localhost";
    @Autowired
    private static RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;

    private static HttpHeaders headers;

    private static String token;

    @Value("${token}")
    public  void setTokenFromProperties(String value){
        token=value;
    }


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
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        headers.add(HttpHeaders.AUTHORIZATION,token);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }


    @Test
    void createAccount()  {
     var createCustomer=customerService.createCustomer(createCustomer("web@gmail.com"));
        keycloak.getInstance().realm(realm).users().delete(createCustomer.getKeycloakId());

     var id=createCustomer.getId().toString();
     var accountType= AccountType.CURRENT.toString();


     var request = new HttpEntity<>(headers);

     var url= baseUrl.concat("/account?customer-id=")
             .concat(id).concat("&account-type=").concat(accountType);

        ResponseEntity<AccountDto> response = restTemplate
                .exchange(url, HttpMethod.POST ,request, AccountDto.class);

        var responseBody=response.getBody();

     assertEquals(HttpStatus.CREATED,response.getStatusCode());
     assertEquals(responseBody.getAccountType().toString(),accountType);
     assertEquals(responseBody.getAccountBalance(), BigDecimal.ZERO);
     assertNotNull(responseBody.getCustomer());
    }

    @Test
    void getCustomerByAccountNumber() {
        var request = new HttpEntity<>(headers);

        var customer=customerService.createCustomer(createCustomer("web@gmail.com"));

        deleteKeycloakUser(customer);

        var account=accountService.createAccount(AccountType.SAVINGS,customer.getId());


        var url=baseUrl.concat("/account?accountNumber=").concat(account.getAccountNumber().toString());
        ResponseEntity<AccountDto> response = restTemplate
                .exchange(url, HttpMethod.GET ,request, AccountDto.class);

        var body=response.getBody();
        assertNotNull(body);
        assertNotNull(body.getCustomer());
        assertNotNull(body.getAccountNumber());
        assertEquals(account.getAccountBalance(),body.getAccountBalance());
        assertEquals(account.getAccountType(),body.getAccountType());
        assertEquals(account.getAccountNumber(),body.getAccountNumber());
    }

    @Test
    void getAllAccounts() {

        var request = new HttpEntity<>(headers);

        var customer=customerService.createCustomer(createCustomer("web@gmail.com"));

        deleteKeycloakUser(customer);

        var createAccount=accountService.createAccount(AccountType.CURRENT,customer.getId());
        var createAccount2=accountService.createAccount(AccountType.CURRENT,customer.getId());
        var createAccount3=accountService.createAccount(AccountType.CURRENT,customer.getId());

        var url=baseUrl.concat ("/accounts");

        ResponseEntity<AccountDto[]> response = restTemplate
                .exchange(url, HttpMethod.GET ,request, AccountDto[].class);

        var body=response.getBody();


        assertNotNull(body);
        assertEquals(4,body.length);
        Arrays.stream(body).forEach(test->{
            assertNotNull(test);
            assertNotNull(test.getNubanNo());
            assertEquals(AccountType.CURRENT,test.getAccountType());
            assertEquals(BigDecimal.ZERO,test.getAccountBalance());

        });



    }

    @Test
    void deleteById() {

        var request = new HttpEntity<>(headers);

        var customer=customerService.createCustomer(createCustomer("web@gmail.com"));

        deleteKeycloakUser(customer);

        var account=accountService.createAccount(AccountType.CURRENT,customer.getId());

        assertEquals(2,accountService.getAllAccounts().size());

        var url=baseUrl.concat ("/account?id=").concat(account.getAccountNumber().toString());

        ResponseEntity response = restTemplate
                .exchange(url, HttpMethod.DELETE ,request, Void.class);

        var body=response.getBody();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertThrows(NoCustomerExceptions.class,()->accountService.getAccountByAccountNumber(account.getAccountNumber()));
        assertEquals(1,accountService.getAllAccounts().size());

    }


    private Register createCustomer(String email){
        AddressDto addressDto = new AddressDto();
        addressDto.setCity("string");
        addressDto.setCountry("string");
        addressDto.setState("string");
        addressDto.setPostalCode("string");
        addressDto.setStreet("string");

        var customerDetails= CustomerDetailsDto.builder()
                .dob(LocalDate.parse("2001-01-01"))
                .maritalStatus("single")
                .occupation("doctor")
                .disability("none").build();

        CustomerDto customerDto = new CustomerDto();
        customerDto.setPassword("string");
        customerDto.setEmail(email);
        customerDto.setAddress(addressDto);
        customerDto.setContactNumber("mmm");
        customerDto.setImageUrl("aws");
        customerDto.setFirstName("sunday");
        customerDto.setLastName("sunday");
        customerDto.setStatus(Status.ACTIVE);
        customerDto.setCustomerDetails(customerDetails);

        Register register = Register.builder()
                .accountType(AccountType.CURRENT)
                .customer(customerDto)
                .build();

        return  register;

    }

    private void deleteKeycloakUser(CustomerDto createCustomer) {
        keycloak.getInstance().realm(realm).users().delete(createCustomer.getKeycloakId());
    }
}
