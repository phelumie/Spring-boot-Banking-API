package com.microfinanceBank.Issues.Controller;

import com.microfinanceBank.Issues.Service.IssueService;
import com.microfinanceBank.Issues.dto.ComplainDto;
import com.microfinanceBank.Issues.dto.IssueDto;
import com.microfinanceBank.Issues.dto.IssueResponse;
import com.microfinanceBank.Issues.repository.IssueRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api")
@SecurityRequirement(name = "Bearer Authentication")
public class IssueController {
    private final IssueService issueService;

    @PostMapping("issue")
    public ResponseEntity<Mono<IssueResponse>> makeComplaint(@Valid @RequestBody ComplainDto complainDto) {
        return new ResponseEntity<>(issueService.makeComplaint(complainDto), HttpStatus.CREATED);
    }

    @GetMapping("issue")
    public ResponseEntity<Flux<IssueDto>> getIssuesByAccountNumber(@RequestParam("acc") Long accountNumber) {
        return new ResponseEntity<>(issueService.getIssuesByAccountNumber(accountNumber),HttpStatus.OK);
    }

    @GetMapping( path = "all-issues",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<IssueDto>> getAllIssues() {
        return new ResponseEntity<>(issueService.getAllIssues(),HttpStatus.OK);
    }

    @GetMapping("pending-issues")
    public ResponseEntity<Flux<IssueDto>> getAllPendingIssues() {
        return new ResponseEntity<>(issueService.getAllPendingIssues(),HttpStatus.OK);
    }

    @PutMapping("issue-fix")
    public ResponseEntity<Mono<IssueResponse>> fixIssue(@RequestParam Long id){

        return new ResponseEntity<>(issueService.fixIssue(id),HttpStatus.ACCEPTED) ;
    }

}
