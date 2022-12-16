package com.microfinanceBank.Loan.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class LoanRiskAnalysis implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean loanWorthinessAnalysis;
    private BigDecimal monthlyPayment;
    private BigDecimal amountLeftAfterExpenses;
    private int creditScoreRating;

}
