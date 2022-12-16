package com.microfinanceBank.Loan.service.impl;

import com.microfinanceBank.Loan.dto.LoanRequest;
import com.microfinanceBank.Loan.dto.LoanRequestResponse;
import com.microfinanceBank.Loan.dto.MakePaymentDto;
import com.microfinanceBank.Loan.entity.BankLoan;
import com.microfinanceBank.Loan.entity.Loan;
import com.microfinanceBank.Loan.entity.LoanPayments;
import com.microfinanceBank.Loan.entity.PeerToPeer;
import com.microfinanceBank.Loan.service.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class IModelMapperImpl implements IModelMapper {
    private final ModelMapper modelMapper;



    @Override
    public Loan convertBankLoanToEntity(LoanRequest loanRequest){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        BankLoan bankLoan=modelMapper.map(loanRequest,BankLoan.class);
        return  bankLoan;

    }

    @Override
    public Loan convertP2pLoanToEntity(LoanRequest loanRequest) {
        PeerToPeer peerToPeer=modelMapper.map(loanRequest,PeerToPeer.class);
        return peerToPeer;
    }
    @Override
    public LoanRequestResponse generateLoanRequestResponse(Loan loan) {
        var loanRequestResponse=modelMapper.map(loan,LoanRequestResponse.class);
        return loanRequestResponse;
    }

}
