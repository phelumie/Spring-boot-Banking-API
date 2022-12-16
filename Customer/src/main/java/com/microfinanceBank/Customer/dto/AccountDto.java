package com.microfinanceBank.Customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microfinanceBank.Customer.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
@JsonProperty
    private Long accountNumber;
@JsonProperty
    private String nubanNo;
@JsonProperty
    private BigDecimal accountBalance;
@JsonProperty
    private AccountType accountType;
@JsonProperty
    private CustomerDto customer;

}
