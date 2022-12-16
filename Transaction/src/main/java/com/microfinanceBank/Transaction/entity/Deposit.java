package com.microfinanceBank.Transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
//@Table(indexes={@Index(columnList="uid,name",name="Index_user")})
@NoArgsConstructor
@DiscriminatorValue(value = "DEPOSIT")
@Entity
public class Deposit extends  Transaction {

    @Column(nullable = false,updatable = false)
    @Positive
    private BigDecimal amount;


}
