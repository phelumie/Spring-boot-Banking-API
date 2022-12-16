package com.microfinanceBank.Issues.Service.Impl;

import com.microfinanceBank.Issues.Exceptions.NoIssueFound;
import com.microfinanceBank.Issues.Service.IssueService;
import com.microfinanceBank.Issues.dto.IssueDto;
import com.microfinanceBank.Issues.dto.ComplainDto;
import com.microfinanceBank.Issues.dto.IssueResponse;
import com.microfinanceBank.Issues.entity.IssueStatus;
import com.microfinanceBank.Issues.entity.Issues;
import com.microfinanceBank.Issues.repository.IssueRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.cms.Time;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;

import static com.microfinanceBank.Issues.entity.Issues.SEQUENCE_NAME;

@RequiredArgsConstructor
@Service
@Slf4j
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public Mono<IssueResponse> makeComplaint(ComplainDto complainDto) {
      log.trace("Entering method makeComplaint");
      log.debug("making complaint by customer id {}",complainDto.getAccountNumber());
        return Mono.just(convertComplaintDtoToEntity(complainDto))
                .flatMap(issueRepository::save)
                .log("successfully issued complaint ", Level.INFO)
                .thenReturn(new IssueResponse("Complaint Successfully submitted"));

    }

    @Transactional(readOnly = true)
    public Flux<IssueDto> getAllIssues(){
        return issueRepository.findAll()
                .map(this::convertEntityToIssueDto);
    }

    @Override
    public Mono<IssueResponse> fixIssue(Long id) {
        log.trace("entering method fixIssue");
        Mono<Issues> issue= issueRepository.findById(id);
        return issue
                .switchIfEmpty(Mono.error(new NoIssueFound("No issue found for id "+id)))
                .doOnNext(x->{
                    log.debug("fixing issue id {}",x.getId());
                    x.setStatus(IssueStatus.FIXED);
                })
                .flatMap(issueRepository::save)
                .thenReturn(new IssueResponse("Issue fixed Successfully"));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<IssueDto> getAllPendingIssues() {
        log.trace("entering method getAllPendingIssues");
        log.debug("Getting all pending issues ");
        return issueRepository.findByStatus(IssueStatus.PENDING)
                .map(this::convertEntityToIssueDto);
    }

    @Override
    public Flux<IssueDto> getIssuesByAccountNumber(Long accountNumber){
        log.trace("entering method getIssuesByAccountNumber");
        log.debug("Getting all issues associated with account number  {}",accountNumber);
        return getIssuesWithAccountNumber(accountNumber)
                .map(issue->convertEntityToIssueDto(issue));


    }
    @Transactional(readOnly = true)
    private Flux<Issues> getIssuesWithAccountNumber(Long accountNumber) {
        return issueRepository.findByAccountNumber(accountNumber);
    }

    private Issues convertComplaintDtoToEntity(ComplainDto complainDto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Issues issues=modelMapper.map(complainDto,Issues.class);
        issues.setStatus(IssueStatus.PENDING);
        issues.setCreationDate(LocalDate.now());
        issues.setTime(LocalDateTime.now());
        issues.setId(sequenceGeneratorService.getSequenceNumber(SEQUENCE_NAME));
        return  issues;
    }
    private IssueDto convertEntityToIssueDto(Issues issue){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        IssueDto issues=modelMapper.map(issue,IssueDto.class);
        return  issues;
    }
}
