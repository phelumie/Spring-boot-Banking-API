package com.microfinanceBank.Customer.dto;

import com.microfinanceBank.Customer.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardRequest {
    @NotNull
    private Long accountNumber;
    @NotNull
    private CardType cardType;
}
