package com.microfinanceBank.Transaction.repository;

import com.microfinanceBank.Transaction.entity.Deposit;
import com.microfinanceBank.Transaction.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface DepositRepository extends TransactionRepository {


}
