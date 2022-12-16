package com.microfinanceBank.Loan.repo;


import com.microfinanceBank.Loan.entity.Loan;
import com.microfinanceBank.Loan.entity.LoanPayments;
import com.microfinanceBank.Loan.projections.AllDueDateLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

//@NoRepositoryBean
public interface LoanRepository extends JpaRepository<Loan,String> {

    List<Loan> findByBorrowerAccountNumber(Long id);


    @Query(value = "select  distinct l  from BankLoan l " +
            "   left join fetch  l.payments p " +
//            "join  l.loanOffer lo " +
            "where l.isFullyPaid= FALSE and l.loanType= 'bank-loan'  and " +
            " l.status= 'ACCEPTED' " +
            "and day(l.dueDate) = day(CURRENT_DATE-2) "
//            countQuery = " select  count(l) from BankLoan l left join l.payments " +
//                    "where l.isFullyPaid= FALSE and l.loanType= 'bank-loan' and " +
//                    "day(l.dueDate) = day(CURRENT_DATE) and l.status= 'ACCEPTED' "

    )
    List<AllDueDateLoan> findAllDueDateBankLoan();

    @Query("update Loan l set l.remainingPrincipal=:principal where l.loanId =:id")
    @Modifying
    void updateLoanPrincipalBalance(@Param("principal") BigDecimal principal, @Param("id") String id);


}
