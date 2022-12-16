package com.microfinanceBank.commondto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class DepositQueue implements Serializable {
    TransactionDto deposit;
    TransactionDetailsDto transactionDetails;
}
