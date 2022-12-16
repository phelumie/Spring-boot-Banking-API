package com.microfinanceBank.Transaction.Service;

import com.microfinanceBank.Transaction.dto.*;
import com.microfinanceBank.Transaction.projections.ITransaction;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface TransactionService {
    CompletableFuture<List<DepositDto>> findAllSuccessfulDepositsTransactions();
    CompletableFuture<List<DepositDto>> findAllFailedDepositTransactions();
    CompletableFuture<List<ITransaction>> allCustomerTransactions(Long accountNum,int offset, int size);


}
