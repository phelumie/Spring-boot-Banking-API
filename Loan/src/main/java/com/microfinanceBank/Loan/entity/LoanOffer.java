package com.microfinanceBank.Loan.entity;

import com.microfinanceBank.Loan.service.impl.LoanCalculationImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanOffer implements Serializable {
    private static final long serialVersionUID= 1L;

    @Id
    private int id;
    @Column(name = "loan_name",nullable = false,unique = true)
    private String loanName;
    @Column(name = "loan_range",nullable = false)
    private String  loanRange;
    @Column(nullable = false)
    private double  interest;
    @Column(name = "late_payment_interest",nullable = false)
    private double latePaymentInterest;


}
