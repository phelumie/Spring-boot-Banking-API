package com.microfinanceBank.Transaction.dto;

import com.microfinanceBank.Transaction.entity.TransactionDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
public class DepositDto {

    private Long id;

    private BigDecimal amount;
    private Long sourceAccount;
    private TransactionDetail transactionDetail;
    private Date transactionDate;
    private Time time;
}
