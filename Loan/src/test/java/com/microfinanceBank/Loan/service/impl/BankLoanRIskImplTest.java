package com.microfinanceBank.Loan.service.impl;

import com.microfinanceBank.Loan.dto.LoanRequest;
import com.microfinanceBank.Loan.entity.BankLoan;
import com.microfinanceBank.Loan.entity.BorrowerDetails;
import com.microfinanceBank.Loan.entity.Loan;
import com.microfinanceBank.Loan.entity.LoanOffer;
import com.microfinanceBank.Loan.enums.AccountType;
import com.microfinanceBank.Loan.enums.LoanStatus;
import com.microfinanceBank.Loan.enums.MaritalStatus;
import com.microfinanceBank.Loan.repo.LoanRepository;
import com.microfinanceBank.Loan.repo.UnApprovedLoansRepository;
import com.microfinanceBank.Loan.service.LoanCalculation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BankLoanRIskImplTest {


    @InjectMocks
    private  BankLoanRIskImpl bankLoanRIsk;

    @Mock
    private LoanCalculation loanCalculation;

    @Mock
    private  LoanRepository loanRepository;

    @Mock
    private UnApprovedLoansRepository unApprovedLoansRepository;

    @Test
    void creditScoreRating() {

        var loanRequest=generateLoanRequest();

        var borrowerAccountId=loanRequest.getBorrowerAccountNumber();

        var record1=generateLoanRecord(borrowerAccountId);
        var record2=generateLoanRecord(borrowerAccountId);
        when(loanRepository.findByBorrowerAccountNumber(borrowerAccountId))
                .thenReturn(List.of(record1,record2));


        var result=bankLoanRIsk.creditScoreRating(loanRequest);
        loanRequest.setJoinDate(LocalDate.now().minusYears(20));
        var result2=bankLoanRIsk.creditScoreRating(loanRequest);
        assertEquals(50,result);
        assertEquals(65,result2);



    }

    @Test
    void loanWorthinessAnalysis() {
        var loanRequest=generateLoanRequest();
        var principal=loanRequest.getPrincipalLoanAmount();
        var interest=loanRequest.getLoanOffer().getInterest();
        var numberOfPayments=loanRequest.getNumberOfPayments();
        loanRequest.getBorrowerDetails().setDob(LocalDate.now());

        when(loanCalculation.calculateMonthlyPayments(principal,interest,numberOfPayments))
                .thenReturn(BigDecimal.valueOf(30000));

        var result=bankLoanRIsk.loanWorthinessAnalysis(loanRequest,convertBankLoanToEntity(loanRequest));

        loanRequest.getBorrowerDetails().setDob(LocalDate.now().minusYears(20));

        var result2=bankLoanRIsk.loanWorthinessAnalysis(loanRequest,convertBankLoanToEntity(loanRequest));


        when(loanCalculation.calculateMonthlyPayments(principal,interest,numberOfPayments))
                .thenReturn(BigDecimal.valueOf(500000));

        var result3=bankLoanRIsk.loanWorthinessAnalysis(loanRequest,convertBankLoanToEntity(loanRequest));
        assertNotNull(result);
        assertDoesNotThrow(()->result);
        assertEquals(LoanStatus.REJECTED,result.getLoan().getStatus());
        assertEquals(false,result.isLoanWorthinessAnalysis());

        assertNotNull(result2);
        assertDoesNotThrow(()->result2);
        assertEquals(LoanStatus.RISK_ANALYSIS,result2.getLoan().getStatus());
        assertEquals(true,result2.isLoanWorthinessAnalysis());

        assertNotNull(result3);
        assertDoesNotThrow(()->result3);
        assertEquals(LoanStatus.REJECTED,result3.getLoan().getStatus());
        assertEquals(false,result3.isLoanWorthinessAnalysis());


    }


    private LoanRequest generateLoanRequest() {

        var loanOffer=new LoanOffer();
        loanOffer.setId(1);
        loanOffer.setLoanName("Personal loan");
        loanOffer.setLoanRange(String.valueOf(5000000));
        loanOffer.setInterest(5);
        loanOffer.setLatePaymentInterest(2.5);

        var borrowerDetails=new BorrowerDetails();
        borrowerDetails.setChildren(0);
        borrowerDetails.setEmail("borrower@gmail.com");
        borrowerDetails.setMaritalStatus(MaritalStatus.SINGLE);
        borrowerDetails.setDob(LocalDate.parse( "1999-01-01"));
        borrowerDetails.setOccupation("trader");
        borrowerDetails.setMobileNumber("08000000000");
        borrowerDetails.setMonthlyIncome(500000);


        var loanRequest= LoanRequest.builder()
                .loanOffer(loanOffer)
                .borrowerDetails(borrowerDetails)
                .haveAnExistingLoan(false)
                .principalLoanAmount(BigDecimal.valueOf(1000000))
                .description("clearing of goods")
                .accountType(AccountType.SAVINGS)
                .joinDate(LocalDate.now())
                .branchId(10L)
                .monthlyExpenses(250000)
                .numberOfPayments(5)
                .borrowerAccountNumber(123456789L).build();

        return loanRequest;


    }

    private Loan generateLoanRecord(Long id){
        var borrowerDetails=new BorrowerDetails();
        borrowerDetails.setMonthlyIncome(700000);
        borrowerDetails.setChildren(2);
        borrowerDetails.setDob(LocalDate.parse("1998-03-05"));
        borrowerDetails.setMaritalStatus(MaritalStatus.SINGLE);

        Loan loan = new BankLoan();

        loan.setBorrowerAccountNumber(id);
        loan.setLoanId(UUID.randomUUID().toString());
        loan.setStatus(LoanStatus.ACCEPTED);
        loan.setApplicationDate(LocalDate.parse("2020-01-01"));
        loan.setPrincipalLoanAmount(BigDecimal.valueOf(500000));
        loan.setRemainingPrincipal(BigDecimal.valueOf(200000));
        loan.setLoanIssuedDate(LocalDate.parse("2020-05-05"));
        loan.setMonthlyDueDate(LocalDate.parse("2021-05-05"));
        loan.setBorrowerDetails(borrowerDetails);
        loan.setFullyPaid(true);
        loan.setFullyPaidDate(LocalDate.now());

        return loan;
    }

    public Loan convertBankLoanToEntity(LoanRequest loanRequest){
        var bankLoan=new BankLoan();
        BeanUtils.copyProperties(loanRequest, bankLoan);
        return  bankLoan;

    }


}