package com.microfinanceBank.Loan.repo;

import com.microfinanceBank.Loan.entity.UnApprovedLoans;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnApprovedLoansRepository extends JpaRepository<UnApprovedLoans,Long> {
}
