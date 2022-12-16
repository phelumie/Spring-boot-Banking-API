package com.microfinanceBank.Loan.service;

import com.microfinanceBank.Loan.dto.LoanRequest;
import com.microfinanceBank.Loan.dto.LoanRequestResponse;

public interface LoanService {

    LoanRequestResponse loanRequest(LoanRequest loanRequest);

    void approveLoan(String id);
}
