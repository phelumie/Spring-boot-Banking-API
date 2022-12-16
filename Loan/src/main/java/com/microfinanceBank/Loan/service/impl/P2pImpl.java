package com.microfinanceBank.Loan.service.impl;

import com.microfinanceBank.Loan.dto.LoanRequest;
import com.microfinanceBank.Loan.entity.Loan;
import com.microfinanceBank.Loan.enums.LoanStatus;
import com.microfinanceBank.Loan.repo.LoanRepository;
import com.microfinanceBank.Loan.service.LoanCalculation;
import com.microfinanceBank.Loan.service.LoanService;
import com.microfinanceBank.Loan.service.P2pService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class P2pImpl implements P2pService {
    private final LoanRepository loanRepository;
    private final  IModelMapperImpl iModelMapper;
    private final LoanCalculation loanCalculation;


    @Override
    @Transactional
    public void loanRequest(LoanRequest loanRequest) {
        var entity=iModelMapper.convertP2pLoanToEntity(loanRequest);
        var loanId=generateLoanId();
        var monthlyInstallmentAmount=
                loanCalculation.calculateMonthlyPayments(loanRequest.getPrincipalLoanAmount(),loanRequest.getLoanOffer().getInterest(),loanRequest.getNumberOfPayments());

        var interestTobePaid=loanCalculation
                .calculateTotalInterestToBePaid(loanRequest.getPrincipalLoanAmount(),loanRequest.getLoanOffer().getInterest(),loanRequest.getNumberOfPayments());

        entity.setMonthlyInstallmentAmount(monthlyInstallmentAmount);
        entity.setInterestToBePaid(interestTobePaid);
        entity.setRemainingPrincipal(loanRequest.getPrincipalLoanAmount());
        entity.setFullyPaid(false);
        entity.setInstallmentCount(0);
        entity.setStatus(LoanStatus.INITIALIZED);
        entity.setLoanId(loanId);
        loanRepository.save(entity);

    }

    private String generateLoanId() {
        String id= UUID.randomUUID().toString();
        Optional<Loan> loan=loanRepository.findById(id);

        while (loan.isPresent()){
        loan=loanRepository.findById(id);
        }
        return id;
    }

}
