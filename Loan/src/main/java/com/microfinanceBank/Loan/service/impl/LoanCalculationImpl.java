package com.microfinanceBank.Loan.service.impl;

import com.microfinanceBank.Loan.dto.LoanRequest;
import com.microfinanceBank.Loan.entity.BankLoan;
import com.microfinanceBank.Loan.entity.Loan;
import com.microfinanceBank.Loan.service.IModelMapper;
import com.microfinanceBank.Loan.service.LoanCalculation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;


@RequiredArgsConstructor
@Service
public class LoanCalculationImpl implements LoanCalculation {

    @Override
    public BigDecimal calculateMonthlyPayments(BigDecimal principal, double interest, int numberOfPayments) {
        float rate= (float) (interest/100/12);
         BigDecimal amount = BigDecimal.valueOf(principal.longValue()
                * (rate * Math.pow(1 + rate, numberOfPayments))
                / (Math.pow(1 + rate, numberOfPayments) - 1));

        return amount.setScale(2, RoundingMode.UP);
    }

    @Override
    public BigDecimal calculateTotalAmountToBePaid(BigDecimal principal, double interest, int numberOfPayments){
       return
        calculateMonthlyPayments(principal,interest,numberOfPayments)

                .multiply(BigDecimal.valueOf(numberOfPayments)).setScale(2,RoundingMode.UP);
    }

    @Override
    public BigDecimal calculateTotalInterestToBePaid(BigDecimal principal, double interest, int numberOfPayments){
        return
                calculateTotalAmountToBePaid(principal,interest,numberOfPayments)
                        .subtract(principal).setScale(2,RoundingMode.UP);


    }

    @Override
    public BigDecimal calculateLatePayments(BigDecimal amount, int numberOfDaysLate,double latePaymentInterest) {
        return
                amount.multiply(BigDecimal.valueOf(numberOfDaysLate/365))
                .multiply(BigDecimal.valueOf(latePaymentInterest/100)).setScale(2,RoundingMode.UP);

    }






}
