package com.microfinanceBank.Loan.service;

import com.microfinanceBank.Loan.dto.MakePaymentDto;

public interface Payment {
    void makeLoanPayment(MakePaymentDto makePaymentDto);
    }
