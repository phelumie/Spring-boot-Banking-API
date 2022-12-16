package com.microfinanceBank.Loan.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Comparator;

@Entity
@NoArgsConstructor
@Data
public class LoanPayments implements Serializable, Comparable<LoanPayments> {
    private static final long serialVersionUID= 1L;

    public LoanPayments(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal paymentAmount;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate PaymentDate;

    @Column(nullable = false)
    @CreationTimestamp
    private Time time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
    @JsonBackReference
    private Loan loan;



    @Override
    public int compareTo(LoanPayments o) {
        return id.compareTo(o.id);

    }
}
