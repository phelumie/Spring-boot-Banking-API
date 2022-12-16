package com.microfinanceBank.Customer.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String name;
    private String transactionType;
    private BigDecimal amount;
    private Long accountNumberTo;
    private BigDecimal accountBalance;
    private String details;

}
