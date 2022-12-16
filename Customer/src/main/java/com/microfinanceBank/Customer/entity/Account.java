package com.microfinanceBank.Customer.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.microfinanceBank.Customer.enums.AccountType;
import com.microfinanceBank.Customer.enums.Status;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {
    @Id
    @Column(unique = true,nullable = false,updatable = false,name = "acct_num")
    private Long accountNumber;

    @Column(unique = true)
    private String nubanNo;

    @Column(name = "balance",nullable = false)
    private BigDecimal accountBalance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonBackReference
    private DebitCard debitCard;


    @CreationTimestamp
    private Date dateCreated;

    @UpdateTimestamp
    private  Date lastActivity;


}
