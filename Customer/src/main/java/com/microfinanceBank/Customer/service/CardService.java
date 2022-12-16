package com.microfinanceBank.Customer.service;

import com.microfinanceBank.Customer.dto.CardRequest;
import com.microfinanceBank.Customer.repository.AccountRepository;

public interface CardService {

    void cardRequest(CardRequest cardRequest);
}
