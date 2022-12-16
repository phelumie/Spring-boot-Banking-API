package com.microfinanceBank.Customer.service;

import com.microfinanceBank.commondto.transaction.TransactionDto;
import com.microfinanceBank.commondto.transaction.TransferTransactionDto;

public interface BankService {
     void deposit(TransactionDto deposit);
     void withdraw(TransactionDto transfer);
     void transfer(TransferTransactionDto transfer);
}
