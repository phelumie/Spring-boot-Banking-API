package com.microfinanceBank.Loan.service.impl;

import com.microfinanceBank.Loan.dto.MakePaymentDto;
import com.microfinanceBank.Loan.entity.LoanPayments;
import com.microfinanceBank.Loan.repo.LoanRepository;
import com.microfinanceBank.Loan.repo.PaymentRepository;
import com.microfinanceBank.Loan.service.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentImpl implements Payment {
    private final IModelMapperImpl modelMapper;
    private  final LoanRepository loanRepository;
    private final PaymentRepository paymentRepository ;

    @Override
    public void makeLoanPayment(MakePaymentDto makePaymentDto) {
        log.trace("entering method makeLoanPayment");

        var loan=loanRepository.findById(makePaymentDto.getLoanId()).get();

        log.debug("initializing payment for loan id {}",makePaymentDto.getLoanId());

        BigDecimal remainingPrincipal = loan.getRemainingPrincipal();

        loan.setRemainingPrincipal(remainingPrincipal.subtract(makePaymentDto.getPaymentAmount()));

        if (remainingPrincipal.compareTo(makePaymentDto.getPaymentAmount())<=0){
            loan.setFullyPaid(true);
            loan.setFullyPaidDate(LocalDate.now());
        }



        loan.addPayments(new LoanPayments(makePaymentDto.getPaymentAmount()));

        loanRepository.save(loan);
        log.info("Payment of amount {} made for loan id {}",makePaymentDto.getPaymentAmount(),makePaymentDto.getLoanId());

    }
}
