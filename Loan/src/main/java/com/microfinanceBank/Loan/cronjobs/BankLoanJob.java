package com.microfinanceBank.Loan.cronjobs;

import com.microfinanceBank.Loan.projections.AllDueDateLoan;
import com.microfinanceBank.Loan.repo.BankLoanRepository;
import com.microfinanceBank.commondto.CronJobQueueDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.microfinanceBank.Loan.config.RabbitMQDirectConfig.LOAN_WITHDRAW_QUEUE;

@Component
@RequiredArgsConstructor
public class BankLoanJob {
    private final BankLoanRepository loanRepository;
    private final AmqpTemplate amqpTemplate;
    private final DirectExchange directExchange;

    Executor executor =Executors.newFixedThreadPool(10);


    @Scheduled(cron = "0 0 11 * * *")
    @Transactional
    public void bankLoanPaymentJob() {
        var allDueDateBankLoan=loanRepository.findAllDueDateBankLoan();
        allDueDateBankLoan.stream().forEach(loan->{
            var lastPayment=getLastPaymentDate(loan);
            var monthBehind=ChronoUnit.MONTHS.between(lastPayment,LocalDate.now());
                        var currentDebt=loan.getRemainingPrincipal();
                        var debtInterest= BigDecimal.ZERO;

                        //calculating late fee interest
                        if (monthBehind>0){
                            var interest= loan.getLoanOffer().getLatePaymentInterest()/100;
                            debtInterest= debtInterest.add(loan.getPrincipalLoanAmount()
                                    .multiply(BigDecimal.valueOf(interest)
                                    .multiply(BigDecimal.valueOf(monthBehind))));



                            var newPrincipal=loan.getRemainingPrincipal().add(debtInterest);

                            loanRepository.updateLoanPrincipalBalance(newPrincipal,loan.getLoanId());

                        }


                        var queue= CronJobQueueDto.builder()
                                .borrowerAccountNumber(loan.getBorrowerAccountNumber())
                                .currentDebt(currentDebt)
                                .debtInterest(debtInterest)
                                .loanId(loan.getLoanId())
                                .build();

                        //            async call to debit customer account

                        amqpTemplate.convertAndSend (directExchange.getName(), LOAN_WITHDRAW_QUEUE, queue);
                    });

        }

        private LocalDate getLastPaymentDate(AllDueDateLoan loan){
        // check if the customer has started paying
        if (loan.getPayments().isEmpty())
            return loan.getLoanIssuedDate();

        if (loan.getPayments().size()==1)
            return loan.getPayments().get(0).getPaymentDate();

        // sort the payment to get the last payment date
        return loan.getPayments().stream().sorted()
                    .collect(Collectors.toList()).get(loan.getPayments().size()-1).getPaymentDate();


        }

}
