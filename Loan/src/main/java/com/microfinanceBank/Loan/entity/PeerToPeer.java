package com.microfinanceBank.Loan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue(value = "p2p")
public class PeerToPeer extends Loan implements Serializable {


    @OneToOne
    @JoinColumn(name = "p2p_loan_offer_id")
    private P2pLoanOffer loanOffer;

}
