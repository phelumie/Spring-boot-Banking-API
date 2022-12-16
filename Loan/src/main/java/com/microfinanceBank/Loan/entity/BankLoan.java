package com.microfinanceBank.Loan.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@DiscriminatorValue(value = "bank-loan")
public class BankLoan extends Loan implements Serializable {

    @OneToOne
    @JoinColumn(name = "bank_loan_offer_id")
    protected LoanOffer loanOffer;


}
