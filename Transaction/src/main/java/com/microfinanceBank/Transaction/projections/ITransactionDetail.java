package com.microfinanceBank.Transaction.projections;

import com.microfinanceBank.Transaction.enums.TransactionStatus;

import java.sql.Date;
import java.sql.Time;

public interface ITransactionDetail {

    Long getTransactionId();

    TransactionStatus getTransactionStatus();

     Date getTransactionDate();

     Time getTime();


}
