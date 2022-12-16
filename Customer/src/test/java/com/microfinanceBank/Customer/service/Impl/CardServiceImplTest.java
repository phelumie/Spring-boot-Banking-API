package com.microfinanceBank.Customer.service.Impl;

import com.microfinanceBank.Customer.Exceptions.InsufficientException;
import com.microfinanceBank.Customer.dto.CardRequest;
import com.microfinanceBank.Customer.entity.Account;
import com.microfinanceBank.Customer.enums.AccountType;
import com.microfinanceBank.Customer.enums.CardType;
import com.microfinanceBank.Customer.enums.Status;
import com.microfinanceBank.Customer.repository.AccountRepository;
import com.microfinanceBank.Customer.repository.DebitCardRepository;
import com.microfinanceBank.Customer.service.GenerateDebitCard;
import com.microfinanceBank.Customer.service.TransactionQueueProducerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CardServiceImplTest {

    @InjectMocks
    private CardServiceImpl cardService;
    @Mock
    private GenerateDebitCard generateDebitCard;

    @Mock
    private DebitCardRepository debitCardRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private  TransactionQueueProducerService transactionQueueProducerService;

    @Test
    void cardRequest() {
        var request=CardRequest.builder()
                .cardType(CardType.MASTER_CARD)
                .accountNumber(12587455L).build();

        var account=account(12587455L);

        when(accountRepository.findById(12587455L)).thenReturn(Optional.of(account));

        assertThrows(InsufficientException.class,()->cardService.cardRequest(request));

        account.setAccountBalance(account.getAccountBalance().add(BigDecimal.valueOf(2000)));

        assertDoesNotThrow(()->cardService.cardRequest(request));

        verify(accountRepository,times(2)).findById(account.getAccountNumber());
        verify(debitCardRepository,times(1)).save(any());
        verify(generateDebitCard,times(1)).generateCardNumber(account.getNubanNo(),16);
        verify(generateDebitCard,times(1)).generateCvvNumber();
    }


    private Account account(Long id){
        Account account=new Account();
        account.setAccountBalance(BigDecimal.valueOf(10));
        account.setAccountType(AccountType.SAVINGS);
        account.setAccountNumber(id);
        account.setNubanNo(account.getAccountNumber().toString());
        account.setStatus(Status.ACTIVE);
        return account;
    }


}