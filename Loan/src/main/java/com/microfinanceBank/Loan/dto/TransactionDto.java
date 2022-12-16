package com.microfinanceBank.Loan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    @NotNull
    private Long sourceAccount;
    @Positive
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String description;
    private LocationDto locationDto;
}
