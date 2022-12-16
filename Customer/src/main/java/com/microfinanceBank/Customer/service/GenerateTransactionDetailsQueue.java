package com.microfinanceBank.Customer.service;

import com.microfinanceBank.commondto.transaction.TransactionDto;
import com.microfinanceBank.commondto.transaction.TransferTransactionDto;
import com.microfinanceBank.commondto.transaction.TransactionStatus;
import com.microfinanceBank.commondto.transaction.DepositQueue;
import com.microfinanceBank.commondto.transaction.TransferQueue;
import com.microfinanceBank.commondto.transaction.WithdrawQueue;

public interface GenerateTransactionDetailsQueue {
    DepositQueue depositQueue(TransactionDto deposit, TransactionStatus status);
    WithdrawQueue withdrawQueue(TransactionDto withdraw, TransactionStatus status);
    TransferQueue transferQueue(TransferTransactionDto transfer, TransactionStatus status);
}
