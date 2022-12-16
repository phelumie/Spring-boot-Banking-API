package com.microfinanceBank.Customer.listener;

import com.microfinanceBank.Customer.dto.CustomerDto;
import com.microfinanceBank.commondto.transaction.LocationDto;
import com.microfinanceBank.commondto.transaction.TransactionDto;
import com.microfinanceBank.commondto.transaction.TransactionStatus;
import com.microfinanceBank.Customer.repository.AccountRepository;
import com.microfinanceBank.Customer.service.AccountService;
import com.microfinanceBank.Customer.service.GenerateTransactionDetailsQueue;
import com.microfinanceBank.commondto.CronJobQueueDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;

import static com.microfinanceBank.Customer.Config.RabbitMQDirectConfig.LOAN_ROUTING_PAYMENT;
import static com.microfinanceBank.Customer.Config.RabbitMQDirectConfig.WITHDRAWAL_ROUTING_TRANSACTION;

@RequiredArgsConstructor
@Component
@Slf4j
@Transactional
public class LoanQueue {
    private final  AccountRepository repository;
    private final AccountService accountService;
    private final GenerateTransactionDetailsQueue generateTransactionDetailsQueue;
    private final AmqpTemplate amqpTemplate;
    private final DirectExchange directExchange;

    @RabbitListener(queues = "LoanWithdrawQueue")
    @Transactional
    public void loanWithdraw(CronJobQueueDto cronJobQueueDto){
        var borrower=cronJobQueueDto.getBorrowerAccountNumber();
        log.trace("Entering method loan withdraw (queue) with customer id {}",borrower);


        log.debug("Preparing to deduct customer for due loan(s) with customer id {}",borrower);
        var account =repository.findById(cronJobQueueDto.getBorrowerAccountNumber()).get();

        BigDecimal amountWithdrawn=BigDecimal.ZERO;

        //remove interest first
        if (cronJobQueueDto.getDebtInterest()!=BigDecimal.ZERO)
            if (account.getAccountBalance().compareTo(cronJobQueueDto.getDebtInterest()) >= 0) {
                log.info("deducting loan late fee interest for id {}",borrower);
                account.setAccountBalance(account.getAccountBalance().subtract(cronJobQueueDto.getDebtInterest()));
                amountWithdrawn=amountWithdrawn.add(cronJobQueueDto.getDebtInterest());

            }


        //remove debt
        if (account.getAccountBalance().compareTo(cronJobQueueDto.getCurrentDebt())>=0) {
                log.info("deducting loan amount for id {}",borrower);
            account.setAccountBalance(account.getAccountBalance().subtract(cronJobQueueDto.getCurrentDebt()));
            amountWithdrawn=amountWithdrawn.add(cronJobQueueDto.getCurrentDebt());

        }

        //clear account balance  if account balance not empty
        else {


            //empty account
            if (account.getAccountBalance().compareTo(BigDecimal.ZERO)!=0){
                amountWithdrawn=amountWithdrawn.add(account.getAccountBalance());
                account.setAccountBalance(BigDecimal.ZERO);
            }
        }

        //asynchronous message to loan and transaction service
        if (amountWithdrawn.compareTo(BigDecimal.ZERO)!=0){
            cronJobQueueDto.setAmountWithdrawn(amountWithdrawn);


            var withdraw= TransactionDto.builder()
                    .amount(amountWithdrawn)
                    .description("Loan settlement")
                    .locationDto(new LocationDto("microfinance bank","head quarter"))
                    .sourceAccount(cronJobQueueDto.getBorrowerAccountNumber()).build();
            repository.save(account);
            amqpTemplate.convertAndSend(directExchange.getName(), WITHDRAWAL_ROUTING_TRANSACTION, generateTransactionDetailsQueue.withdrawQueue(withdraw, TransactionStatus.SUCCESS));
            amqpTemplate.convertAndSend(directExchange.getName(), LOAN_ROUTING_PAYMENT,cronJobQueueDto);

        }
    }



}
