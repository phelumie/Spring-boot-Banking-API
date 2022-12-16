package com.microfinanceBank.Transaction.entity;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue(value = "WITHDRAW")
public class Withdraw extends Transaction{

    @Column(nullable = false,updatable = false)
    private BigDecimal amount;


}
