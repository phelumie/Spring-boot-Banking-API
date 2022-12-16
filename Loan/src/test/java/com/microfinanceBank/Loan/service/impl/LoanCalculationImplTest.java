package com.microfinanceBank.Loan.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoanCalculationImplTest {
    @InjectMocks
    private LoanCalculationImpl loanCalculation;

        private static BigDecimal principal= BigDecimal.valueOf(100000);
        private static double interest=2.5;
        private static int numberOfPayments=6;

    @Test
    void calculateMonthlyPayments() {

        var result=loanCalculation.calculateMonthlyPayments(principal,interest,numberOfPayments);
        var flooredResult=result.intValue();

        assertDoesNotThrow(()->result);
        assertNotNull(flooredResult);
        assertEquals(16788,flooredResult);

    }

    @Test
    void calculateTotalAmountToBePaid() {
        var result=loanCalculation.calculateTotalAmountToBePaid(principal,interest,numberOfPayments);

        var flooredResult=result.intValue();

        assertDoesNotThrow(()->result);
        assertNotNull(flooredResult);
        assertEquals(100732,flooredResult);

    }

    @Test
    void calculateTotalInterestToBePaid() {

        var result=loanCalculation.calculateTotalInterestToBePaid(principal,interest,numberOfPayments);

        var flooredResult=result.intValue();

        assertDoesNotThrow(()->result);

        assertNotNull(flooredResult);

        assertEquals(732,flooredResult);

    }


}
