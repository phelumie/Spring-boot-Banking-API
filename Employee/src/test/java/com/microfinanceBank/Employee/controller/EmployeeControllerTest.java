package com.microfinanceBank.Employee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microfinanceBank.Employee.dto.*;
import com.microfinanceBank.Employee.entity.Department;
import com.microfinanceBank.Employee.enums.Gender;
import com.microfinanceBank.Employee.enums.Role;
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
class EmployeeControllerTest {


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
    private  KeycloakProvider keycloak;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private BranchService branchService;
    private ObjectMapper objectMapper;
    @Autowired
    private EmployeeRepository employeeRepository;
    private static HttpHeaders headers;
    private static String token="Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJGdF95bWVETmxmYkVjazVfTXZLOW9mQzRzVmJqTnd2Zmw5SlM1YVVYbTY4In0.eyJleHAiOjE2NjM1MDM4NzUsImlhdCI6MTY2MzA3MTg3NSwianRpIjoiYzkxYzhmODEtYzIxYi00NTEwLWEzYmItMTM1Y2U3ZDEwN2M5IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo5MDkwL3JlYWxtcy9TcHJpbmdib290LWJhbmstbWljcm9zZXJ2aWNlcyIsInN1YiI6IjZmMmEyODZmLTMwMTgtNDdhOS05ZjM3LWY0OGFkZWFlYzg5YiIsInR5cCI6IkJlYXJlciIsImF6cCI6ImZyb250LWVuZCIsInNlc3Npb25fc3RhdGUiOiI4ZjUyNmJmOS1lOWQ2LTQxNjYtOGY1Ny0yMzcyZTE5MzcwZGMiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbIioiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiYWRtaW4iLCJkZWZhdWx0LXJvbGVzLXNwcmluZ2Jvb3QtYmFuay1taWNyb3NlcnZpY2VzIiwiaHIiLCJ1bWFfYXV0aG9yaXphdGlvbiIsImVtcGxveWVlIl19LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIiwic2lkIjoiOGY1MjZiZjktZTlkNi00MTY2LThmNTctMjM3MmUxOTM3MGRjIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6ImFqaXNlZ2lyaXMxQGdtYWlsLmNvIiwiZW1haWwiOiJhamlzZWdpcmlzMUBnbWFpbC5jbyJ9.4xWHQHIaGCDkqHlWlhvCISI4y8De7-KYwMBkQvYkojnr2kX8bb12vy9aSnGuz2UJh8xlh5yzgi3byHgJvPR99-BxWisuqxP1MlSHPgW8wp6ZWqMXxBUu-gKmWO0XRz2EGDnyUxG7h1aIHlweEIOltiTKgaqOzIFqnR3je7TR7P-UgzqHrjK1yGDp2yz2knLz2kLHDArNCAZHjB7NJznBsXX63qrCTTBtY-PVy0Lu0ClB12W6YwfYe8JhkFoIBVnAJbOSO4Mi1phbpCPYPk5nsCdy98-MJMnN0OnGJp6t6Ly6go3zl6xq9FYAeazsrLpM2YLq-BaYaFq1K5U0UuiTvw";

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
    void registerUser() {

        branchService.createBranch(getBranch());

        var requestBody =createEmployee();
        var request = new HttpEntity<>(requestBody,headers);

        ResponseEntity<RegisterResponse> response = restTemplate
                .exchange(baseUrl.concat("/employee"), HttpMethod.POST ,request, RegisterResponse.class);
        var body=response.getBody();
        keycloak.getInstance().realm(realm).users().delete(body.getKeycloakId());

        assertNotNull(body);
        assertEquals(requestBody.getEmail(),body.getEmail());
        assertNotNull(body.getMessage());
        assertEquals(requestBody.getFirstName().concat(" ").concat(requestBody.getLastName()),body.getName());

    }

    @Test
    void deleteEmployee() {
        branchService.createBranch(getBranch());
        var employee= employeeService.registerEmployee(createEmployee());

        var request = new HttpEntity<>(headers);

        ResponseEntity response = restTemplate
                .exchange(baseUrl.concat("/employee?id=").concat(employee.getId().toString()), HttpMethod.DELETE ,request, ResponseEntity.class);

        assertNotNull(response.getStatusCode());
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertThrows(Exception.class,()->employeeRepository.findById(employee.getId()).get());
        assertEquals(0,employeeService.getAllEmployeeList().size());
    }

    @Test
    void makeAdmin() {
        branchService.createBranch(getBranch());
        var requestBody =createEmployee();

        var emp=employeeService.registerEmployee(requestBody);
        var request = new HttpEntity<>(headers);

        ResponseEntity<RegisterResponse> response = restTemplate
                .exchange(baseUrl.concat("/make-admin/").concat(emp.getId().toString()), HttpMethod.PUT ,request, RegisterResponse.class);
        keycloak.getInstance().realm(realm).users().delete(emp.getKeycloakId());

        var body=response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(emp.getName(), body.getName());
        assertEquals(emp.getId(), body.getId());
        assertEquals(Role.ADMIN,employeeRepository.findByEmail(emp.getEmail()).get().getRole());

    }

    @Test
    void demoteAdminToUser() {
        branchService.createBranch(getBranch());

        var requestBody =createEmployee();

        var emp=employeeService.registerEmployee(requestBody);
        var request = new HttpEntity<>(headers);

        ResponseEntity<RegisterResponse> response = restTemplate
                .exchange(baseUrl.concat("/demote-admin/").concat(emp.getId().toString()), HttpMethod.PUT ,request, RegisterResponse.class);
        keycloak.getInstance().realm(realm).users().delete(emp.getKeycloakId());

        var body=response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(emp.getName(), body.getName());
        assertEquals(emp.getId(), body.getId());
        assertEquals(Role.EMPLOYEE,employeeRepository.findByEmail(emp.getEmail()).get().getRole());

    }

    @Test
    void getAllUsersList() {
        branchService.createBranch(getBranch());
        var requestBody =createEmployee();
        var requestBody2 =createEmployee();
        var requestBody3 =createEmployee();
        requestBody2.setEmail("web.@gmail.com");
        requestBody3.setEmail("web2.@gmail.com");

        var emp=employeeService.registerEmployee(requestBody);
        var emp2=employeeService.registerEmployee(requestBody2);
        var emp3=employeeService.registerEmployee(requestBody3);


        var request = new HttpEntity<>(headers);
        keycloak.getInstance().realm(realm).users().delete(emp.getKeycloakId());
        keycloak.getInstance().realm(realm).users().delete(emp2.getKeycloakId());
        keycloak.getInstance().realm(realm).users().delete(emp3.getKeycloakId());

        ResponseEntity<EmployeeDto[]> response = restTemplate
                .exchange(baseUrl.concat("/all-employees-list/"), HttpMethod.GET ,request, EmployeeDto[].class);

        var body=response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(employeeRepository.findAll());
        assertEquals(3,employeeRepository.findAll().size());
        assertEquals(3,body.length);
    }


    private RegisterDto createEmployee(){

        return
         RegisterDto.builder()
                .branch(1L)
                .email("webdeveloper@gmail.com")
                .dept(new Department("1","Human Resources"))
                .role(Role.HR)
                .firstName("Sunday")
                .lastName("Ajisegiri")
                .gender(Gender.MALE)
                .profileImageUrl("url")
                .password("web123456")
                .build();
    }

    private BranchDto getBranch() {
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
