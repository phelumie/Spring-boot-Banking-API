package com.microfinanceBank.Transaction.projections;

import java.math.BigDecimal;

public interface ITransfer extends ITransaction{

     BigDecimal getAmount();
     Long getRecipientAccount();


}
