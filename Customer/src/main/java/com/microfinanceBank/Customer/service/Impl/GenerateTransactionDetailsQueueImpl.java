package com.microfinanceBank.Customer.service.Impl;

import com.microfinanceBank.commondto.transaction.TransactionDto;
import com.microfinanceBank.commondto.transaction.TransferTransactionDto;
import com.microfinanceBank.commondto.transaction.TransactionStatus;
import com.microfinanceBank.commondto.transaction.TransactionType;
import com.microfinanceBank.commondto.transaction.DepositQueue;
import com.microfinanceBank.commondto.transaction.TransactionDetailsDto;
import com.microfinanceBank.commondto.transaction.TransferQueue;
import com.microfinanceBank.commondto.transaction.WithdrawQueue;
import com.microfinanceBank.Customer.service.GenerateTransactionDetailsQueue;
import org.springframework.stereotype.Component;

@Component
public class GenerateTransactionDetailsQueueImpl implements GenerateTransactionDetailsQueue {


    @Override
    public DepositQueue depositQueue(TransactionDto deposit, TransactionStatus status) {
        var queue=new DepositQueue();
        queue.setDeposit(deposit);
        queue.setTransactionDetails(new TransactionDetailsDto(status, TransactionType.DEPOSIT,deposit.getLocationDto()));
        return queue;
    }

    @Override
    public WithdrawQueue withdrawQueue(TransactionDto withdraw, TransactionStatus status) {
        var queue=new WithdrawQueue();
        queue.setWithdraw(withdraw);
        queue.setTransactionDetails(new TransactionDetailsDto(status,TransactionType.WITHDRAW,withdraw.getLocationDto()));
        return queue;
    }

    @Override
    public TransferQueue transferQueue(TransferTransactionDto transfer, TransactionStatus status) {
        var queue=new TransferQueue();
        queue.setTransfer(transfer);
        queue.setTransactionDetails(new TransactionDetailsDto(status,TransactionType.TRANSFER,transfer.getLocationDto()));
        return queue;
    }
}
