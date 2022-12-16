package com.microfinanceBank.Transaction.entity;

import com.microfinanceBank.Transaction.enums.TransactionStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@Entity
public class TransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    @JoinColumn
    @OneToOne(fetch = FetchType.EAGER,cascade =CascadeType.ALL)
    private Location location;
    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Date transactionDate;
    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Time time;
}
