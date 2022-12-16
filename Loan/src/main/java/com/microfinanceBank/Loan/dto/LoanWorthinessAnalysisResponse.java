package com.microfinanceBank.Loan.dto;

import com.microfinanceBank.Loan.entity.Loan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanWorthinessAnalysisResponse implements Serializable {
    private boolean loanWorthinessAnalysis;
    private BigDecimal monthlyPayment;
    private BigDecimal amountLeftAfterExpenses;
    private Loan loan;
}
