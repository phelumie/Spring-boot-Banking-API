package com.microfinanceBank.Loan.service.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.microfinanceBank.Loan.dto.LoanWorthinessAnalysisResponse;
import com.microfinanceBank.Loan.dto.RabbitMessage;
import com.microfinanceBank.Loan.entity.Loan;
import com.microfinanceBank.Loan.entity.LoanRiskAnalysis;
import com.microfinanceBank.Loan.enums.LoanStatus;
import com.microfinanceBank.Loan.repo.LoanRepository;
import com.microfinanceBank.Loan.service.BankLoanRisk;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitListenerService {
    private final BankLoanRisk bankLoanRIsk;
    private final LoanRepository loanRepository;

    @RabbitListener(queues = "LoanAnalysisQueue")
    public void worthinessAnalysis(RabbitMessage message){
        message.getBankLoan().setStatus(LoanStatus.WORTHINESS_ANALYSIS);
        loanRepository.save(message.getBankLoan());

        var worthinessAnalysis=bankLoanRIsk.loanWorthinessAnalysis(message.getLoanRequest(),message.getBankLoan());

        var creditScoreRating=bankLoanRIsk.creditScoreRating(message.getLoanRequest());

        Loan loan=worthinessAnalysis.getLoan();

        var analysis=new LoanRiskAnalysis();
        analysis.setAmountLeftAfterExpenses(worthinessAnalysis.getAmountLeftAfterExpenses());
        analysis.setLoanWorthinessAnalysis(worthinessAnalysis.isLoanWorthinessAnalysis());
        analysis.setMonthlyPayment(worthinessAnalysis.getMonthlyPayment());
        analysis.setCreditScoreRating(creditScoreRating);


            if (worthinessAnalysis.isLoanWorthinessAnalysis())
                loan.setStatus(LoanStatus.UNDER_CONSIDERATION);
            loan.setLoanRiskAnalysis(analysis);
            loanRepository.save(loan);


    }

}
