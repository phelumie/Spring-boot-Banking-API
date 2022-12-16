package com.microfinanceBank.Loan.service;

import com.microfinanceBank.Loan.dto.LoanRequest;
import com.microfinanceBank.Loan.dto.LoanWorthinessAnalysisResponse;
import com.microfinanceBank.Loan.dto.RabbitMessage;
import com.microfinanceBank.Loan.entity.Loan;

public interface BankLoanRisk {

    int creditScoreRating(LoanRequest loanRequest);
    boolean loanWorthinessAnalysis(LoanRequest loanRequest);
    LoanWorthinessAnalysisResponse loanWorthinessAnalysis(LoanRequest loanRequest, Loan loan);

}
