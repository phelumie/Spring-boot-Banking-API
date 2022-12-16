package com.microfinanceBank.Loan.repo;


import com.microfinanceBank.Loan.entity.Loan;
import com.microfinanceBank.Loan.entity.LoanPayments;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<LoanPayments,Long> {
}
