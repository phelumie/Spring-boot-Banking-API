package com.microfinanceBank.commondto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetailsDto  implements Serializable {
    private TransactionStatus transactionStatus;
    private TransactionType transactionType;
    private LocationDto locationDto;
}
