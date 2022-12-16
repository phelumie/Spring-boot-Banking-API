package com.microfinanceBank.Loan.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microfinanceBank.Loan.dto.LoanRequest;
import com.microfinanceBank.Loan.dto.LoanRequestResponse;
import com.microfinanceBank.Loan.dto.RabbitMessage;
import com.microfinanceBank.Loan.enums.LoanStatus;
import com.microfinanceBank.Loan.repo.LoanRepository;
import com.microfinanceBank.Loan.service.LoanCalculation;
import com.microfinanceBank.Loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static com.microfinanceBank.Loan.config.RabbitMQDirectConfig.LOAN_ANALYSIS_QUEUE;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final  IModelMapperImpl iModelMapper;
    private final LoanCalculation loanCalculation;
    private final DirectExchange directExchange;
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    @Transactional
    public LoanRequestResponse loanRequest(LoanRequest loanRequest) {
        log.trace("Entering method loanRequest");
        var entity=iModelMapper.convertBankLoanToEntity(loanRequest);
        var loanId=generateLoanId();

        log.debug("processing loan request");

        var monthlyInstallmentAmount=
                loanCalculation.calculateMonthlyPayments(loanRequest.getPrincipalLoanAmount(),loanRequest.getLoanOffer().getInterest(),loanRequest.getNumberOfPayments());

        var interestTobePaid=loanCalculation
                .calculateTotalInterestToBePaid(loanRequest.getPrincipalLoanAmount(),loanRequest.getLoanOffer().getInterest(),loanRequest.getNumberOfPayments());

        entity.setMonthlyInstallmentAmount(monthlyInstallmentAmount);
        entity.setInterestToBePaid(interestTobePaid);
        entity.setRemainingPrincipal(loanRequest.getPrincipalLoanAmount());
        entity.setFullyPaid(false);
        entity.setApplicationDate(LocalDate.now());
        entity.setInstallmentCount(0);
        entity.setStatus(LoanStatus.INITIALIZED);
        entity.setLoanId(loanId);
        var saved=loanRepository.save(entity);

        amqpTemplate.convertAndSend(directExchange.getName(), LOAN_ANALYSIS_QUEUE,new RabbitMessage(loanRequest,saved));

        log.info("successfully processed loan request of account id {} with loan id {}",loanRequest.getBorrowerAccountNumber(),loanId);

        var response=iModelMapper.generateLoanRequestResponse(saved);
        return  response;
    }

    @Override
    public void approveLoan(String id) {
        log.trace("entering method approveLoan");
        var loan=loanRepository.findById(id).get();
        log.debug("approving loan id {}",id);
        loan.setStatus(LoanStatus.ACCEPTED);
        loan.setLoanIssuedDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusMonths(loan.getNumberOfPayments()));
        loanRepository.save(loan);
        log.info("approved  loan  {}",loan);

    }

    private String generateLoanId() {
        String id= UUID.randomUUID().toString();
        var loan=loanRepository.existsById(id);

        while (loan){
            id=UUID.randomUUID().toString();
        }
        return id;
    }


}
