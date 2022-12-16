package com.microfinanceBank.Loan.dto;

import com.microfinanceBank.Loan.entity.BorrowerDetails;
import com.microfinanceBank.Loan.enums.LoanStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class LoanRequestResponse {

    private String loanId;
    private Long borrowerAccountNumber;
    private String description;
    private LoanStatus status;
    private boolean isFullyPaid;
    private Integer installmentCount;
    private Integer numberOfPayments;
    private BigDecimal monthlyInstallmentAmount;
    private BigDecimal interestToBePaid;
    private BigDecimal principalLoanAmount;
    private BigDecimal remainingPrincipal;
    private LocalDate applicationDate;
}
