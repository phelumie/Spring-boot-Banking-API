package com.microfinanceBank.Customer.service.Impl;

import com.microfinanceBank.Customer.service.GenerateTransactionDetailsQueue;
import com.microfinanceBank.Customer.service.TransactionQueueProducerService;
import com.microfinanceBank.commondto.transaction.TransactionDto;
import com.microfinanceBank.commondto.transaction.TransactionStatus;
import com.microfinanceBank.commondto.transaction.TransferTransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import static com.microfinanceBank.Customer.Config.RabbitMQDirectConfig.*;

@Component
@RequiredArgsConstructor
public class TransactionQueueProducerServiceImpl implements TransactionQueueProducerService {
    private final GenerateTransactionDetailsQueue generateTransactionDetailsQueue;
    private final AmqpTemplate amqpTemplate;
    private final DirectExchange directExchange;


    @Override
    public void transfer(TransferTransactionDto transfer, TransactionStatus status) {
        var details=generateTransactionDetailsQueue.transferQueue(transfer,status);
        amqpTemplate.convertAndSend(directExchange.getName(), TRANSFER_ROUTING_TRANSACTION, details);
    }

    @Override
    public void withdraw(TransactionDto withdraw, TransactionStatus status) {
        var details=generateTransactionDetailsQueue.withdrawQueue(withdraw,status);
        amqpTemplate.convertAndSend(directExchange.getName(), WITHDRAWAL_ROUTING_TRANSACTION,details);

    }

    @Override
    public void deposit(TransactionDto deposit, TransactionStatus status) {
        var details=generateTransactionDetailsQueue.depositQueue(deposit,status);
        amqpTemplate.convertAndSend(directExchange.getName(), DEPOSIT_ROUTING_TRANSACTION,details);
    }
}
