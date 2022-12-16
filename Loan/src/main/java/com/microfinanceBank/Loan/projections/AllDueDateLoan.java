package com.microfinanceBank.Loan.projections;

import com.microfinanceBank.Loan.entity.LoanOffer;
import com.microfinanceBank.Loan.entity.LoanPayments;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface AllDueDateLoan  {
    LocalDate getDueDate();
    LocalDate getLoanIssuedDate();
    BigDecimal getRemainingPrincipal();
    LoanOffer getLoanOffer();
    BigDecimal getPrincipalLoanAmount();
    Long getBorrowerAccountNumber();
    List<LoanPayments> getPayments();
    String getLoanId();

    LocalDate setDueDate(LocalDate date);
    LocalDate setLoanIssuedDate(LocalDate date);
    BigDecimal setRemainingPrincipal(BigDecimal remainingPrincipal);
    LoanOffer setLoanOffer(LoanOffer offer);
    List<LoanPayments> SetPayments(List<LoanPayments> payments);
    BigDecimal setPrincipalLoanAmount(BigDecimal principalLoanAmount);
    Long setBorrowerAccountNumber(Long borrowerAccount);
    String setLoanId(String loanId);

}
