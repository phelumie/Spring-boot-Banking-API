package com.microfinanceBank.Loan.listener;

import com.microfinanceBank.Loan.entity.LoanPayments;
import com.microfinanceBank.Loan.repo.LoanRepository;
import com.microfinanceBank.commondto.CronJobQueueDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateLoanPayment {
    private final LoanRepository loanRepository;

    @RabbitListener(queues = "LoanPaymentQueue")
    public  void updateLoanPayment(CronJobQueueDto cronJobQueueDto){
        log.trace("entering method updateLoanPayment ");

        var loan=loanRepository.findById(cronJobQueueDto.getLoanId()).get();

        log.debug("updating  payment details for loan id {} ",cronJobQueueDto.getLoanId());
        loan.setRemainingPrincipal(loan.getRemainingPrincipal().subtract(cronJobQueueDto.getAmountWithdrawn()));
        if (cronJobQueueDto.getAmountWithdrawn().compareTo(loan.getRemainingPrincipal())>= 0) {
            loan.setFullyPaid(true);
            loan.setFullyPaidDate(LocalDate.now());
        }


        loan.addPayments(new LoanPayments(cronJobQueueDto.getAmountWithdrawn()));

        loanRepository.save(loan);

        log.info("Payment of amount {} made for loan id {}",cronJobQueueDto.getAmountWithdrawn(),cronJobQueueDto.getLoanId());

    }

}
