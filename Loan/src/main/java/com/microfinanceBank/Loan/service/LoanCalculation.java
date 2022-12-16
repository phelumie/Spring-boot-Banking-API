package com.microfinanceBank.Loan.service;

import com.microfinanceBank.Loan.dto.LoanRequest;

import java.math.BigDecimal;

public interface LoanCalculation {

    BigDecimal calculateMonthlyPayments(BigDecimal principal, double interest, int numberOfPayments);

    BigDecimal calculateTotalAmountToBePaid(BigDecimal principal, double interest, int numberOfPayments);

    BigDecimal calculateTotalInterestToBePaid(BigDecimal principal, double interest, int numberOfPayments);

    BigDecimal calculateLatePayments(BigDecimal amount, int numberOfDaysLate, double latePaymentInterest );


}
