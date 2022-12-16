

package com.microfinanceBank.commondto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CronJobQueueDto implements  Serializable{
    private BigDecimal debtInterest;
    private BigDecimal currentDebt;
    private Long borrowerAccountNumber;
    private String loanId;
    private BigDecimal amountWithdrawn;


}
