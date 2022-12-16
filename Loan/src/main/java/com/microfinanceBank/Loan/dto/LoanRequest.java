package com.microfinanceBank.Loan.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.microfinanceBank.Loan.entity.BorrowerDetails;
import com.microfinanceBank.Loan.entity.LoanOffer;
import com.microfinanceBank.Loan.enums.AccountType;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NotNull
public class LoanRequest implements Serializable{
    private static final long serialVersionUID= 1L;

    @NotNull
    private Long borrowerAccountNumber;
    @Positive
    @NotNull
    private Long branchId;
    private AccountType accountType;
    @NotNull
    @Positive
    private BigDecimal principalLoanAmount;
    @NotNull
    private boolean haveAnExistingLoan;
    private double amountUsedToSettleExistingLoanMonthly;
    @NotNull
    @Positive
    private double monthlyExpenses;
    @NotNull
    @Positive
    private int numberOfPayments;
    @NotNull
    private String description;
    @NotNull
    private LoanOffer loanOffer;
    @NotNull
    private BorrowerDetails borrowerDetails;
    @NotNull
    private LocalDate joinDate;

}