package com.microfinanceBank.Transaction.projections;

import com.microfinanceBank.Transaction.enums.TransactionType;

import java.sql.Date;
import java.sql.Time;

public interface ITransaction {

     Long getId();
     Long getSourceAccount();
//     Long getRecipientAccount();
     ITransactionDetail getTransactionDetail();
     Date getTransactionDate();
     Time getTime();
     TransactionType getTransactionType();
}
