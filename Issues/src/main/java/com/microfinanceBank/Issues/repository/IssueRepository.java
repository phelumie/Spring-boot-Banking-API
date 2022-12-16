package com.microfinanceBank.Issues.repository;

import com.microfinanceBank.Issues.entity.IssueStatus;
import com.microfinanceBank.Issues.entity.Issues;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface IssueRepository extends ReactiveCrudRepository<Issues,Long> {
    Flux<Issues> findByAccountNumber(Long accountNumber);
    Flux<Issues> findByStatus(IssueStatus status);
}
