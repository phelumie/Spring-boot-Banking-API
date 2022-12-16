package com.microfinanceBank.Customer.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microfinanceBank.Customer.Config.KeycloakProvider;
import com.microfinanceBank.Customer.dto.AddressDto;
import com.microfinanceBank.Customer.dto.CustomerDetailsDto;
import com.microfinanceBank.Customer.dto.CustomerDto;
import com.microfinanceBank.Customer.dto.Register;
import com.microfinanceBank.Customer.enums.AccountType;
import com.microfinanceBank.Customer.enums.Status;
import com.microfinanceBank.Customer.service.CustomerService;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cglib.proxy.Mixin;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {

    @LocalServerPort
    private int port;

    @Value("${Keycloak.realm}")
    private String realm;
    @Autowired
    private Flyway flyway;
    private String baseUrl="http://localhost";
    @Autowired
    private static RestTemplate restTemplate;
    @Autowired
    private KeycloakProvider keycloak;

    private ObjectMapper objectMapper;
    @Autowired
    private CustomerService  customerService;
    private static HttpHeaders headers;

    private static String token;

    @BeforeAll
    public static void init(){
        restTemplate= new RestTemplate();
        headers=new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE);
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

    }

    @Value("${token}")
    public  void setTokenFromProperties(String value){
        token=value;
    }


    @Test
    void createCustomer(){

        var register=createCustomer("myemail@ee.com");

        var request = new HttpEntity<>(register,headers);


        ResponseEntity<CustomerDto> response = restTemplate
                .exchange(baseUrl.concat("/customer"),HttpMethod.POST ,request, CustomerDto.class);

        keycloak.getInstance().realm(realm).users().delete(response.getBody().getKeycloakId());

        assertEquals(HttpStatus.CREATED,response.getStatusCode());

        assertEquals(register.getCustomer().getEmail(), response.getBody().getEmail());
        assertEquals(register.getCustomer().getFirstName(), response.getBody().getFirstName());
    }
    @Test
//    @Sql(statements = "INSERT INTO CUSTOMER (id,name,email,accountNumber,keycloakId," +
//            "accountType,creationDate,time) VALUES (1,'sunday','mmm@gmail.com','123456789','asdfghf'" +
//            " 'SAVINGS','2022-03-19','06:37:15' ",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "")
    void getAllCustomers() throws Exception {

        var register1=createCustomer("welokf.@gg.vc");
        var register2=createCustomer("wegyghdfrggf.@gg.vc");

        customerService.createCustomer(register1);
        customerService.createCustomer(register2);

        var request = new HttpEntity<>(headers);


        ResponseEntity<CustomerDto[]> response=restTemplate.exchange(baseUrl.concat("/customers"), HttpMethod.GET,request,CustomerDto[].class);
        assertEquals(2,response.getBody().length);


    }


    @Test
    public void deleteById(){
        var register=createCustomer("webdeveloper@gmail.com");
        var request = new HttpEntity<>(register,headers);

        ResponseEntity<CustomerDto> postResponse = restTemplate
                .exchange(baseUrl.concat("/customer"),HttpMethod.POST ,request, CustomerDto.class);

        keycloak.getInstance().realm(realm).users().delete(postResponse.getBody().getKeycloakId());

        var id=postResponse.getBody().getId().toString();
        ResponseEntity response=restTemplate.exchange(baseUrl.concat("/customer?id=").concat(id), HttpMethod.DELETE,request,Void.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());


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
}