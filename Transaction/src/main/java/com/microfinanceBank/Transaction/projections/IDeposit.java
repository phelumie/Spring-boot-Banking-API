package com.microfinanceBank.Transaction.projections;

import java.math.BigDecimal;

public interface IDeposit extends ITransaction{

     BigDecimal getAmount();

}
