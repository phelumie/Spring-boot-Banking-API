package com.microfinanceBank.Transaction.projections;

import java.math.BigDecimal;

public interface IWithdraw extends ITransaction {

     BigDecimal getAmount();

}
