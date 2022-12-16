package com.microfinanceBank.Customer.service;

import com.microfinanceBank.Customer.dto.AccountDto;
import com.microfinanceBank.Customer.entity.Account;
import com.microfinanceBank.Customer.enums.AccountType;

import java.util.List;

public interface AccountService {
    AccountDto getAccountByAccountNumber(Long accountNumber);
    List<AccountDto> getAllAccounts();
    Account createAccount(AccountType accountType);
    AccountDto createAccount(AccountType accountType, Long customerId);
    void deleteAccount(Long id);
}
