package com.microfinanceBank.commondto.transaction;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class WithdrawQueue  implements Serializable {
    TransactionDto withdraw;
    TransactionDetailsDto transactionDetails;
}
