package com.microfinanceBank.Transaction.projections;

import com.microfinanceBank.Transaction.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetail {
    private Long transactionId;
    private TransactionStatus transactionStatus;
    private Date transactionDate;
    private Time time;

}
