package com.microfinanceBank.Issues.Service;

import com.microfinanceBank.Issues.dto.ComplainDto;
import com.microfinanceBank.Issues.dto.IssueDto;
import com.microfinanceBank.Issues.dto.IssueResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IssueService {
    Mono<IssueResponse> makeComplaint(ComplainDto complainDto);
    Flux<IssueDto> getIssuesByAccountNumber(Long accountNumber);
    Flux<IssueDto> getAllIssues();
    Mono<IssueResponse> fixIssue(Long id);
    Flux<IssueDto> getAllPendingIssues();
}
