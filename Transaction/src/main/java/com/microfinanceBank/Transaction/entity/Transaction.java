package com.microfinanceBank.Transaction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microfinanceBank.Transaction.enums.TransactionType;
import jdk.jfr.MemoryAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "TRANSACTION_TYPE",
        discriminatorType = DiscriminatorType.STRING
)
public  class Transaction  implements Serializable{
    private static final long serialVersionUID= 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false,name = "transactionId")
    private Long id;
    @Column(nullable = false,updatable = false)
    private Long sourceAccount;
    @Column(nullable = false,updatable = false)
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(nullable = false,name = "transaction_detail_id")
    @MapsId
    private TransactionDetail transactionDetail;
    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Date transactionDate;
    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Time time;


    @Column(name="transaction_type", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    protected TransactionType transactionType;

//    @Transient
//    protected Long recipientAccount;
//
//    public Long getRecipientAccount(){
//        return recipientAccount;
//    }


    public TransactionType getTransactionType() {
        return transactionType;
    }


}
