package com.microfinanceBank.Loan.service;

import com.microfinanceBank.Loan.dto.LoanRequest;
import com.microfinanceBank.Loan.dto.LoanRequestResponse;
import com.microfinanceBank.Loan.dto.MakePaymentDto;
import com.microfinanceBank.Loan.entity.Loan;
import com.microfinanceBank.Loan.entity.LoanPayments;

public interface IModelMapper {
    Loan convertBankLoanToEntity(LoanRequest loanRequest);
    Loan convertP2pLoanToEntity(LoanRequest loanRequest);
    LoanRequestResponse generateLoanRequestResponse(Loan loan);
}
