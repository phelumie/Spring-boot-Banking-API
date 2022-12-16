package com.microfinanceBank.Transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "TRANSFER")
public class Transfer extends Transaction{


    @Column(nullable = false,updatable = false,name = "desc_acct")
    private Long recipientAccount;
    @Column(nullable = false,updatable = false)
    private BigDecimal amount;



}
