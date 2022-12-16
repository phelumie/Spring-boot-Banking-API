package com.microfinanceBank.Loan.service.impl;

import com.microfinanceBank.Loan.dto.LoanRequest;
import com.microfinanceBank.Loan.dto.LoanWorthinessAnalysisResponse;
import com.microfinanceBank.Loan.entity.Loan;
import com.microfinanceBank.Loan.entity.UnApprovedLoans;
import com.microfinanceBank.Loan.enums.AccountType;
import com.microfinanceBank.Loan.enums.LoanStatus;
import com.microfinanceBank.Loan.enums.MaritalStatus;
import com.microfinanceBank.Loan.exceptions.UnderAgeException;
import com.microfinanceBank.Loan.repo.LoanRepository;
import com.microfinanceBank.Loan.repo.UnApprovedLoansRepository;
import com.microfinanceBank.Loan.service.BankLoanRisk;
import com.microfinanceBank.Loan.service.LoanCalculation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.microfinanceBank.Loan.constants.LoanConstants.DEBT_RATIO_PERCENT;
import static com.microfinanceBank.Loan.constants.LoanConstants.MARRIED_INTEREST_DEDUCTION;

@Service
public class BankLoanRIskImpl implements BankLoanRisk {
    private final LoanRepository loanRepository;
    private final LoanCalculation loanCalculation;
    private final UnApprovedLoansRepository unApprovedLoansRepository;
    private  HashMap<AccountType, Integer> loanLimit;



    public BankLoanRIskImpl(LoanRepository loanRepository, LoanCalculation loanCalculation, UnApprovedLoansRepository unApprovedLoansRepository) {
        this.loanRepository = loanRepository;
        this.loanCalculation = loanCalculation;
        this.unApprovedLoansRepository = unApprovedLoansRepository;

        this.loanLimit=new HashMap<AccountType, Integer>();
        loanLimit.put(AccountType.CURRENT,10000000);
        loanLimit.put(AccountType.SAVINGS,20000000);
        loanLimit.put(AccountType.FIXED_DEPOSIT,90000000);

    }

    @Override
    public int creditScoreRating(LoanRequest loanRequest) {
        int grade= 0;
        var applicantLoanRecord=loanRepository.findByBorrowerAccountNumber(loanRequest.getBorrowerAccountNumber());
        int yearsOfBanking= (int) ChronoUnit.YEARS.between(loanRequest.getJoinDate(),LocalDate.now());
        // check loan payment history
        grade+=gradeByPaymentHistory(loanRequest.getBorrowerAccountNumber(),applicantLoanRecord);
        //check if amount of current debt > loan limit
        grade+=gradeByDebtRatio(loanRequest,applicantLoanRecord);


        // has been a customer over 10 years
        if (yearsOfBanking>10)
            grade+=15;

        //has experience in borrowing =10%
        if (applicantLoanRecord.size()-1!=0)
            grade+=10;


        int size=applicantLoanRecord.stream()
                .filter(loan->loan.getApplicationDate().getYear()==Year.now().getValue())
        .collect(Collectors.toList()).size();

        //applied for loans less than 3 times this year
        if (size<3)
            grade+=10;

        return grade;
    }

    @Override
    public LoanWorthinessAnalysisResponse loanWorthinessAnalysis(LoanRequest loanRequest, Loan loan) {
        UnApprovedLoans unApprovedLoan;
        var borrowerDetails= loanRequest.getBorrowerDetails();
        var interest=loanRequest.getLoanOffer().getInterest();

        var todayDate=LocalDate.now();

        var applicantDob=borrowerDetails.getDob();
        var age=ChronoUnit.YEARS.between(applicantDob,todayDate);

        if (age<18){

            loan.setStatus(LoanStatus.REJECTED);

             unApprovedLoan=UnApprovedLoans.builder()
                     .loanId(loan.getLoanId())
                    .reasons(Collections.singleton("under age"))
                     .build();

            unApprovedLoansRepository.save(unApprovedLoan);


            return new LoanWorthinessAnalysisResponse(false,BigDecimal.ZERO,BigDecimal.ZERO,loan);
        }

        var amountLeftAfterExpenses =
                loanRequest.getBorrowerDetails().getMonthlyIncome()- loanRequest.getMonthlyExpenses();


        if (loanRequest.isHaveAnExistingLoan())
            amountLeftAfterExpenses-=loanRequest.getAmountUsedToSettleExistingLoanMonthly();



        if (loanRequest.getBorrowerDetails().getMaritalStatus().equals(MaritalStatus.MARRIED))

            amountLeftAfterExpenses-= loanRequest.getBorrowerDetails().getMonthlyIncome() *(MARRIED_INTEREST_DEDUCTION/100);

        var monthlyPayment=
        loanCalculation.calculateMonthlyPayments(loanRequest.getPrincipalLoanAmount(),interest,loanRequest.getNumberOfPayments());

        if (monthlyPayment.compareTo(BigDecimal.valueOf(amountLeftAfterExpenses))>=0){
            unApprovedLoan=UnApprovedLoans.builder()
                    .loanId(loan.getLoanId())
                    .reasons(Collections.singleton("Failed worthiness analysis"))
                    .build();
                unApprovedLoansRepository.save(unApprovedLoan);
                loan.setStatus(LoanStatus.REJECTED);
        return new LoanWorthinessAnalysisResponse(false,monthlyPayment,BigDecimal.valueOf(amountLeftAfterExpenses),loan);
    }
        loan.setStatus(LoanStatus.RISK_ANALYSIS);
        return new LoanWorthinessAnalysisResponse(true,monthlyPayment,BigDecimal.valueOf(amountLeftAfterExpenses),loan);


}


    @Override
    public boolean loanWorthinessAnalysis(LoanRequest loanRequest) {
        UnApprovedLoans unApprovedLoan;
        var borrowerDetails= loanRequest.getBorrowerDetails();
        var interest=loanRequest.getLoanOffer().getInterest();

        var todayDate=LocalDate.now();

        var applicantDob=borrowerDetails.getDob();
        var age=ChronoUnit.YEARS.between(applicantDob,todayDate);

        if (age<18){
            throw new UnderAgeException("Applicant must be at least 18 years old at the time of application ");
        }

        var amountLeftAfterExpenses =
                loanRequest.getBorrowerDetails().getMonthlyIncome()- loanRequest.getMonthlyExpenses();


        if (loanRequest.isHaveAnExistingLoan())
            amountLeftAfterExpenses-=loanRequest.getAmountUsedToSettleExistingLoanMonthly();


        if (loanRequest.getBorrowerDetails().getMaritalStatus().equals(MaritalStatus.MARRIED))

            amountLeftAfterExpenses-= loanRequest.getMonthlyExpenses()*0.15;

        var monthlyPayment=
                loanCalculation.calculateMonthlyPayments(loanRequest.getPrincipalLoanAmount(),interest,loanRequest.getNumberOfPayments());

        if (monthlyPayment.compareTo(BigDecimal.valueOf(amountLeftAfterExpenses))>=0){
            return false;
        }
        return true;
    }

    private int gradeByPaymentHistory(Long borrowerId, List<Loan> applicantLoanRecord){
        int grade =35;

        if (applicantLoanRecord.size()==0)
            return grade;
        int days=applicantLoanRecord.parallelStream()
                .filter(date->Objects.nonNull(date.getFullyPaidDate()))
                .filter(loan->loan.getFullyPaidDate().isAfter(loan.getDueDate()))
                .map(loan-> (int)ChronoUnit.DAYS.between(loan.getDueDate(),loan.getFullyPaidDate()))
                .reduce(0,Integer::sum);

        grade-=days/30;
        return grade;
    }

    private int gradeByDebtRatio(LoanRequest loanRequest,List<Loan> loan) {
        var accountType=loanRequest.getAccountType();
        double ratio= DEBT_RATIO_PERCENT/100;
        double limit=ratio*loanLimit.get(accountType);
        BigDecimal currentDebt= loan.parallelStream()
                .filter(result-> Objects.nonNull(result.getLoanIssuedDate()))
                .filter(data -> !data.isFullyPaid())
                .map(balance->balance.getRemainingPrincipal())
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        if (currentDebt.compareTo(BigDecimal.valueOf(limit))<0)
            return 30;

        return 0;
    }


}