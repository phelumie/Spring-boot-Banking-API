package com.microfinanceBank.Loan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class P2pLoanOffer {
    @Id
    private int id;
    @Column(name = "loan_name",nullable = false,unique = true)
    private String loanName;
    private String  loanRange;
    private double  interest;
    private double latePaymentInterest;


}
