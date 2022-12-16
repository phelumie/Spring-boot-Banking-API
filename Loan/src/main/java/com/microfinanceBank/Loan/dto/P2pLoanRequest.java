package com.microfinanceBank.Loan.dto;

import com.microfinanceBank.Loan.entity.BorrowerDetails;
import com.microfinanceBank.Loan.entity.LoanOffer;
import com.microfinanceBank.Loan.entity.P2pLoanOffer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class P2pLoanRequest  implements Serializable {
    private static final long serialVersionUID= 1L;

    private Long borrowerAccountNumber;
    @Positive
    private Long branchId;
    private BigDecimal principalLoanAmount;
    private int numberOfPayments;
    private String description;
    private P2pLoanOffer loanOffer;
    private BorrowerDetails borrowerDetails;
}
