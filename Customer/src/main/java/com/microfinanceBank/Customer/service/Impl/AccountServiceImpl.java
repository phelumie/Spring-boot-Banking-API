package com.microfinanceBank.Customer.service.Impl;

import com.microfinanceBank.Customer.Exceptions.NoCustomerExceptions;
import com.microfinanceBank.Customer.dto.AccountDto;
import com.microfinanceBank.Customer.entity.Account;
import com.microfinanceBank.Customer.entity.Customer;
import com.microfinanceBank.Customer.enums.AccountType;
import com.microfinanceBank.Customer.enums.Status;
import com.microfinanceBank.Customer.job.HappyBirthDay;
import com.microfinanceBank.Customer.repository.AccountRepository;
import com.microfinanceBank.Customer.repository.CustomerRepository;
import com.microfinanceBank.Customer.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public AccountDto getAccountByAccountNumber(Long accountNumber){
        log.trace("Entering method getCustomerByAccountNumber");
        log.debug("Getting customer with account number --> {} ",accountNumber);
        String message="No account number found for id: ".concat(accountNumber.toString()).concat(" ");
        return accountRepository.findById(accountNumber).map(this::convertAccountEntityToDto)
                .orElseThrow(() ->{
                    log.error("No account number found for id: {}",accountNumber);
                    return new NoCustomerExceptions(message);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream().map(this::convertAccountEntityToDto).collect(Collectors.toList());
    }

    @Override
    public Account createAccount(AccountType accountType){
        Account acc=new Account();
        acc.setAccountNumber(generateAccountNumber());
        acc.setNubanNo(String.valueOf(acc.getAccountNumber()));
        acc.setAccountType(accountType);
        acc.setStatus(Status.ACTIVE);
        acc.setAccountBalance(BigDecimal.valueOf(0));
        return acc;
    }

    @Override
    public AccountDto createAccount(AccountType accountType, Long customerId) {
        Customer customer= customerRepository.findById(customerId)
                .orElseThrow(()->new NoCustomerExceptions("No customer with account number: "+customerId));
        Account account=new Account();
        account.setAccountType(accountType);
        account.setStatus(Status.ACTIVE);
        account.setAccountNumber(generateAccountNumber());
        account.setNubanNo(String.valueOf(account.getAccountNumber()));
        account.setCustomer(customer);
        account.setAccountBalance(BigDecimal.valueOf(0));
        accountRepository.save(account);
        return convertAccountEntityToDto(account);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    private Long generateAccountNumber(){
        ThreadLocalRandom random = ThreadLocalRandom.current();

        long accNumber =  random.nextLong(10_000_000_000L, 100_000_000_000L);

        while (accountRepository.existsById(accNumber))
            accNumber=random.nextLong(10_000_000_000L, 100_000_000_000L);

        return accNumber;
    }

    private AccountDto convertAccountEntityToDto(Account account){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        AccountDto accountDto=modelMapper.map(account,AccountDto.class);
        return  accountDto;
    }

}
