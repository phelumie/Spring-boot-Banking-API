package com.microfinanceBank.Customer.service;

public interface GenerateDebitCard {

    String generateCardNumber(String nuban, int length);
    int generateCvvNumber();
}
