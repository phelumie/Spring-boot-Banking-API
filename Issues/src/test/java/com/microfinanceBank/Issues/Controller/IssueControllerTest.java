//package com.microfinanceBank.Issues.Controller;
//
//
//import com.microfinanceBank.Issues.Service.IssueService;
//import com.microfinanceBank.Issues.dto.ComplainDto;
//import com.microfinanceBank.Issues.dto.IssueDto;
//import com.microfinanceBank.Issues.dto.IssueResponse;
//import com.microfinanceBank.Issues.entity.IssueStatus;
//import com.microfinanceBank.Issues.repository.IssueRepository;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import reactor.test.StepVerifier;
//
//import javax.ws.rs.core.Application;
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ExtendWith(SpringExtension.class)
//@AutoConfigureWebTestClient
////@TestPropertySource("")
//@ActiveProfiles("test")
//@AutoConfigureDataMongo
//class IssueControllerTest {
//
//    @LocalServerPort
//    private int port;
//    @Autowired
//    private IssueRepository issueRepository;
//    private String baseUrl = "http://localhost";
//    @Autowired
//    private WebTestClient webClient;
//    @Autowired
//    private IssueService issueService;
//
//    private static String token;
//
//    @Value("${token}")
//    public  void setTokenFromProperties(String value){
//        token=value;
//        System.out.println("tokensss "+value);
//
//    }
//
//    @Value("${spring.data.mongodb.password}")
//    public  void stTokenFromProperties(String value){
//        System.out.println("");
//        System.out.println(value);
//    }
//
//    @BeforeAll
//    public static void init() {
//
//    }
//
//    @BeforeEach
//    public void cleanUp() {
//        issueRepository.deleteAll();
//    }
//
//    @BeforeEach
//    public void setup() {
//        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api");
//    }
//
//
//    @Test
//    void makeComplaint() {
//        var issue =generateIssue();
//
//        var response = webClient.post()
//                .uri(baseUrl.concat("/issue"))
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(issue)
//                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
//                .exchange()
//                .expectStatus().isCreated()
//                .expectBody(IssueResponse.class)
//                .value(msg -> msg.setMessage("Complaint Successfully submitted"))
//                .returnResult();
//
//        assertEquals(HttpStatus.CREATED, response.getStatus());
//
//        StepVerifier.create(issueRepository.findAll().collectList().map(x -> x.size()))
//                .expectNext(1)
//                .verifyComplete();
//
//    }
//
//    @Test
//    void getIssuesByAccountNumber() {
//        var issue = generateIssue();
//        var createIssue = issueService.makeComplaint(issue);
//
//        StepVerifier
//                .create(createIssue)
//                .expectNext(new IssueResponse("Complaint Successfully submitted"))
//                .verifyComplete();
//
//        var response = webClient.get()
//                .uri(baseUrl.concat("/issue?acc=").concat(issue.getAccountNumber().toString()))
//                .accept(MediaType.APPLICATION_JSON)
//                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(IssueDto[].class)
//                .returnResult();
//
//        var body = response.getResponseBody();
//
//        assertDoesNotThrow(() -> body);
//        assertNotNull(body);
//        assertEquals(1,body.length);
//        Arrays.stream(body).forEach(data->{
//            assertEquals(issue.getAccountNumber(), data.getAccountNumber());
//            assertEquals(IssueStatus.PENDING, data.getStatus());
//            assertNotNull(data.getId());
//            assertNotNull(data.getIssue());
//            assertNotNull(data.getCreationDate());
//            assertNotNull(data.getTime());
//            assertNotNull(data.getStatus());
//        });
//
//        StepVerifier.create(issueRepository.findByAccountNumber(issue.getAccountNumber()))
//                .expectNextCount(1)
//                .verifyComplete();
//
//    }
//
//    @Test
//    void getAllIssues() {
//
//        var issue = generateIssue();
//
//        var createIssue = issueService.makeComplaint(issue);
//        var createIssue2 = issueService.makeComplaint(issue);
//
//        StepVerifier
//                .create(createIssue)
//                .expectNext(new IssueResponse("Complaint Successfully submitted"));
//
//        var response = webClient.get()
//                .uri(baseUrl.concat("/all-issues"))
//                .accept(MediaType.TEXT_EVENT_STREAM)
//                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(IssueDto[].class)
//                .returnResult();
//
//        System.out.println(response.getResponseHeaders().getAccept());
//        System.out.println("content-type");
//        System.out.println(response.getResponseHeaders().getContentType());
//
//        var body = response.getResponseBody();
//
//        assertTrue(response.getResponseHeaders().getAccept().contains(MediaType.TEXT_EVENT_STREAM));
//        assertDoesNotThrow(() -> body);
//        assertNotNull(body);
//        assertEquals(2,body.length);
//
//        Arrays.stream(body).forEach(data->{
//            assertEquals(issue.getAccountNumber(), data.getAccountNumber());
//            assertEquals(IssueStatus.PENDING, data.getStatus());
//            assertNotNull(data.getId());
//            assertNotNull(data.getIssue());
//            assertNotNull(data.getCreationDate());
//            assertNotNull(data.getTime());
//            assertNotNull(data.getStatus());
//        });
//
//        StepVerifier.create(issueRepository.findAll())
//                        .expectNextCount(2)
//                .verifyComplete();
//
//    }
//
//    @Test
//    void getAllPendingIssues(){
//        var issue = generateIssue();
//
//        var createIssue = issueService.makeComplaint(issue);
//        var createIssue2 = issueService.makeComplaint(issue);
//        var createIssue3 = issueService.makeComplaint(issue);
//        var createIssue4 = issueService.makeComplaint(issue);
//
//        var fixIssue4=issueService.fixIssue(4L);
//
//        StepVerifier
//                .create(createIssue)
//                .expectNext(new IssueResponse("Complaint Successfully submitted"));
//
//        var response = webClient.get()
//                .uri(baseUrl.concat("/pending-issues"))
//                .accept(MediaType.APPLICATION_JSON)
//                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(IssueDto[].class)
//                .returnResult();
//
//
//        var body = response.getResponseBody();
//
//        assertTrue(response.getResponseHeaders().getAccept().contains(MediaType.TEXT_EVENT_STREAM));
//        assertDoesNotThrow(() -> body);
//        assertNotNull(body);
//        assertEquals(3,body.length);
//        Arrays.stream(body).forEach(data->{
//            assertEquals(issue.getAccountNumber(), data.getAccountNumber());
//            assertEquals(IssueStatus.PENDING, data.getStatus());
//            assertNotNull(data.getId());
//            assertNotNull(data.getIssue());
//            assertNotNull(data.getCreationDate());
//            assertNotNull(data.getTime());
//            assertNotNull(data.getStatus());
//        });
//
//        StepVerifier.create(issueRepository.findAll().filter(x->x.getStatus().equals(IssueStatus.PENDING)))
//                .expectNextCount(3)
//                .verifyComplete();
//    }
//
//    @Test
//    void fixIssue(){
//
//        var issue = generateIssue();
//
//        var createIssue = issueService.makeComplaint(issue);
//
//
//        StepVerifier
//                .create(createIssue)
//                .expectNext(new IssueResponse("Complaint Successfully submitted"));
//
//        var response = webClient.put()
//                .uri(baseUrl.concat("/issue-fix?id=1"))
//                .accept(MediaType.APPLICATION_JSON)
//                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
//                .exchange()
//                .expectStatus().isAccepted()
//                .expectBody(IssueResponse.class)
//                .returnResult();
//
//
//        var body = response.getResponseBody();
//
//        assertNotNull(body);
//        assertEquals("Issue fixed Successfully",body.getMessage());
//        StepVerifier.create(issueRepository.findAll().filter(x->x.getStatus().equals(IssueStatus.FIXED)))
//                .expectNextCount(1)
//                .verifyComplete();
//    }
//
//    private ComplainDto generateIssue() {
//
//        var issue = ComplainDto.builder()
//                .issue("pending transfer")
//                .accountNumber(123456789L)
//                .build();
//        return issue;
//
//    }
//
//}