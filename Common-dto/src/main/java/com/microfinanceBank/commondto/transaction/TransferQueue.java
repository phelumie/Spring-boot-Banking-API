package com.microfinanceBank.commondto.transaction;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TransferQueue implements Serializable {
    TransferTransactionDto transfer;
    TransactionDetailsDto transactionDetails;
}
