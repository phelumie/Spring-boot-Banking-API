package com.microfinanceBank.Loan.dto;

import com.microfinanceBank.Loan.entity.BankLoan;
import com.microfinanceBank.Loan.entity.Loan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMessage implements Serializable   {
    private static final long serialVersionUID= 1L;

    private LoanRequest loanRequest;
    private Loan bankLoan;
}
