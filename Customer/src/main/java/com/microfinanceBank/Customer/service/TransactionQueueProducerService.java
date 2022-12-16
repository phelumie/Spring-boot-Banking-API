package com.microfinanceBank.Customer.service;


import com.microfinanceBank.commondto.transaction.TransactionDto;
import com.microfinanceBank.commondto.transaction.TransactionStatus;
import com.microfinanceBank.commondto.transaction.TransferTransactionDto;

public interface TransactionQueueProducerService {
    void transfer(TransferTransactionDto transfer, TransactionStatus status);
    void withdraw(TransactionDto withdraw, TransactionStatus status);
    void deposit(TransactionDto deposit, TransactionStatus status);
}
