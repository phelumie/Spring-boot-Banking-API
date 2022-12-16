package com.microfinanceBank.Customer.repository;

import com.microfinanceBank.Customer.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    @Query(value = "Select account_balance from account a where a.acct_num=?1",nativeQuery = true)
    BigDecimal getCustomerBalance(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findById(Long id);
}
