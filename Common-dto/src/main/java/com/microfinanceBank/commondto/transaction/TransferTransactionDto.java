package com.microfinanceBank.commondto.transaction;

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
public class TransferTransactionDto implements Serializable {

    @NotNull
    @Positive
    private Long recipientAccount;
    @NotNull
    @Positive
    private Long sourceAccount;
    @NotNull
    @Positive
    private BigDecimal amount;
    @NotNull
    private String description;
    private LocationDto locationDto;

}
