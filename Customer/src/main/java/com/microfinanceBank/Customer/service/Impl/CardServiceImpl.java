package com.microfinanceBank.Customer.service.Impl;

import com.microfinanceBank.Customer.Exceptions.InsufficientException;
import com.microfinanceBank.Customer.dto.CardRequest;
import com.microfinanceBank.Customer.entity.DebitCard;
import com.microfinanceBank.Customer.enums.CardStatus;
import com.microfinanceBank.Customer.repository.AccountRepository;
import com.microfinanceBank.Customer.repository.DebitCardRepository;
import com.microfinanceBank.Customer.service.CardService;
import com.microfinanceBank.Customer.service.GenerateDebitCard;
import com.microfinanceBank.Customer.service.TransactionQueueProducerService;
import com.microfinanceBank.commondto.transaction.LocationDto;
import com.microfinanceBank.commondto.transaction.TransactionDto;
import com.microfinanceBank.commondto.transaction.TransactionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CardServiceImpl implements CardService {

    private final AccountRepository accountRepository;
    private final GenerateDebitCard generateDebitCard;
    private final TransactionQueueProducerService transactionQueueProducerService;
    private final DebitCardRepository debitCardRepository;

    private static final BigDecimal CARD_CHARGES= BigDecimal.valueOf(1100);
    private static final int  CARD_NUMBER_LENGTH= 16;
    @Override
    public void cardRequest(CardRequest cardRequest) {
        var id=cardRequest.getAccountNumber();
        log.trace("Entering method card request with customer id {}",id);

        var account=accountRepository.findById(cardRequest.getAccountNumber()).get();
        if (account.getAccountBalance().compareTo(CARD_CHARGES)<=0){
            log.error("Insufficient funds to request card for customer with id {}",id);
            throw new InsufficientException("Insufficient Funds!!");
        }
        account.setAccountBalance(account.getAccountBalance().subtract(CARD_CHARGES));
        var debitCardEntity=new DebitCard();

        var cardNumber=generateDebitCard.generateCardNumber(account.getNubanNo(),CARD_NUMBER_LENGTH);
        var cvv=generateDebitCard.generateCvvNumber();

        debitCardEntity.setCardStatus(CardStatus.ACTIVE);
        debitCardEntity.setCardType(cardRequest.getCardType());
        debitCardEntity.setExpireDate(LocalDate.now().plusYears(4));
        debitCardEntity.setCardNo(cardNumber);
        debitCardEntity.setCvvNo(cvv);
        debitCardEntity.addAccount(account);

        debitCardRepository.save(debitCardEntity);

        var withdrawQueue=TransactionDto.builder().locationDto(new LocationDto("fcmb","head quarter"))
                        .amount(CARD_CHARGES)
                                .description("Debit card request charges")
                                        .sourceAccount(cardRequest.getAccountNumber())
                                                .build();

        log.info("card request was successful for customer with id {}",id);

        transactionQueueProducerService.withdraw(withdrawQueue, TransactionStatus.SUCCESS);
    }
}
