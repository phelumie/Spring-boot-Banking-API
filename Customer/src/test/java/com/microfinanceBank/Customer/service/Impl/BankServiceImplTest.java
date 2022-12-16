   package com.microfinanceBank.Customer.service.Impl;

import com.microfinanceBank.Customer.Exceptions.AccountNotActive;
import com.microfinanceBank.Customer.Exceptions.InsufficientException;
import com.microfinanceBank.Customer.Exceptions.NoCustomerExceptions;
import com.microfinanceBank.Customer.entity.Account;
import com.microfinanceBank.Customer.enums.AccountType;
import com.microfinanceBank.Customer.enums.Status;
import com.microfinanceBank.Customer.repository.AccountRepository;
import com.microfinanceBank.Customer.service.BankService;
import com.microfinanceBank.Customer.service.TransactionQueueProducerService;
import com.microfinanceBank.commondto.transaction.LocationDto;
import com.microfinanceBank.commondto.transaction.TransactionDto;
import com.microfinanceBank.commondto.transaction.TransferTransactionDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BankServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private  TransactionQueueProducerService transactionQueueProducerService;


    @InjectMocks
    private BankServiceImpl bankService;


    @Test
    void deposit() {
        var deposit=TransactionDto.builder()
                .sourceAccount(123456768L)
                .amount(BigDecimal.valueOf(1000))
                .description("Deposit")
                .locationDto(new LocationDto("fcmb","Lagos"))
                .build();

        var account=account(deposit.getSourceAccount());
        when(accountRepository.findById(deposit.getSourceAccount())).thenReturn(Optional.of(account));


        assertDoesNotThrow(()->bankService.deposit(deposit));

        account.setStatus(Status.BLOCKED);

        assertThrows(AccountNotActive.class,()->bankService.deposit(deposit));

    }

    @Test
    void withdraw() {

        var withdraw=TransactionDto.builder()
                .sourceAccount(123456768L)
                .amount(BigDecimal.valueOf(10000000))
                .description("Withdraw")
                .locationDto(new LocationDto("fcmb","Lagos"))
                .build();

        var account=account(withdraw.getSourceAccount());
        when(accountRepository.findById(withdraw.getSourceAccount())).thenReturn(Optional.of(account));



        assertThrows(InsufficientException.class,()->bankService.withdraw(withdraw));

        account.setAccountBalance(BigDecimal.valueOf(100000000000000000L));

        assertDoesNotThrow(()->bankService.withdraw(withdraw));


        withdraw.setSourceAccount(1214L);

        assertThrows(NoCustomerExceptions.class,()->bankService.withdraw(withdraw));



    }

    @Test
    void transfer() {

        var transfer= TransferTransactionDto.builder()
                .sourceAccount(789852457L)
                .recipientAccount(789852457L)
                .amount(BigDecimal.valueOf(1000000))
                .description("Transfer")
                .locationDto(new LocationDto("fcmb","Lagos"))
                .build();

        var account=account(789852457L);
        var account2=account(123457895L);

        when(accountRepository.findById(789852457L)).thenReturn(Optional.of(account));
        when(accountRepository.findById(123457895L)).thenReturn(Optional.of(account2));


        assertThrows(IllegalStateException.class,()->bankService.transfer(transfer));


        transfer.setSourceAccount(123457895L);

        assertThrows(InsufficientException.class,()->bankService.transfer(transfer));

        account2.setAccountBalance(BigDecimal.valueOf(10000000L));

        assertDoesNotThrow(()->bankService.transfer(transfer));
    }

    private Account account(Long id){
        Account account=new Account();
        account.setAccountBalance(BigDecimal.valueOf(10000));
        account.setAccountType(AccountType.SAVINGS);
        account.setAccountNumber(id);
        account.setNubanNo(account.getAccountNumber().toString());
        account.setStatus(Status.ACTIVE);
        return account;
    }




}