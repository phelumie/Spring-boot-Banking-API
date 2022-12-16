package com.microfinanceBank.commondto.transaction;

import com.microfinanceBank.commondto.transaction.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto implements Serializable {

    @NotNull
    private Long sourceAccount;
    @NotNull
    @Positive
    private BigDecimal amount;
    @NotNull
    @NotBlank
    private String description;
    private LocationDto locationDto;
}
