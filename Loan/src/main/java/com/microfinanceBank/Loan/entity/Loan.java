package com.microfinanceBank.Loan.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.microfinanceBank.Loan.enums.LoanStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "LOAN_TYPE",
        discriminatorType = DiscriminatorType.STRING
)
@EqualsAndHashCode
public class Loan implements Serializable {
    private static final long serialVersionUID= 1L;

    @Id
    @Column(nullable = false,updatable = false)
    private String loanId;
    @Column(updatable = false,nullable = false,name = "borrower_id")
    private Long borrowerAccountNumber;
    private Long branchId;
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    private LoanStatus status;
    @Column
    private boolean isFullyPaid;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "borrower_detail_id")
    private BorrowerDetails borrowerDetails;
    private Integer installmentCount;
    private Integer numberOfPayments;
    private BigDecimal monthlyInstallmentAmount;
    private BigDecimal interestToBePaid;
    private BigDecimal principalLoanAmount;
    private BigDecimal remainingPrincipal;
    @CreationTimestamp
    private LocalDate applicationDate;
    private LocalDate loanIssuedDate;
    private LocalDate dueDate;
    private LocalDate fullyPaidDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private LoanRiskAnalysis loanRiskAnalysis;

    @OneToMany(mappedBy = "loan",cascade = {
            CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH
    }
    ,fetch = FetchType.LAZY
    )
    @JsonManagedReference
    @Fetch(FetchMode.SELECT)
    private Set<LoanPayments> payments;



    @Column(name = "loan_type",insertable = false,updatable = false)
    protected String loanType;


    public void addPayments(LoanPayments payment){
            if (payment!=null){
                if (this.payments==null)
                    this.payments=new HashSet<>();
                this.payments.add(payment);
                payment.setLoan(this);
            }
    }

}
