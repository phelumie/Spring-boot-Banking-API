package com.microfinanceBank.Loan.cronjobs;

import com.microfinanceBank.Loan.entity.LoanOffer;
import com.microfinanceBank.Loan.projections.AllDueDateLoan;
import com.microfinanceBank.Loan.repo.BankLoanRepository;
import com.microfinanceBank.Loan.repo.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BankLoanJobTest {

    @InjectMocks
    private BankLoanJob bankLoanJob;
    @Mock
    private BankLoanRepository loanRepository;
    @Mock
    private AmqpTemplate amqpTemplate;
    @Mock
    private DirectExchange directExchange;

    @Test
    void bankLoanPaymentJob() {
        ProjectionFactory factory=new SpelAwareProxyProjectionFactory();

        AllDueDateLoan projection=factory.createProjection(AllDueDateLoan.class);

        projection.setLoanId(UUID.randomUUID().toString());
        projection.setLoanOffer(new LoanOffer(1,"personal loan","1000000",2.8,1.3));
        projection.setRemainingPrincipal(BigDecimal.valueOf(200000));
        projection.setBorrowerAccountNumber(123456789L);
        projection.setDueDate(LocalDate.parse("2022-10-10"));

        Mockito.when(loanRepository.findAllDueDateBankLoan()).thenReturn((List.of(projection,projection)));

        assertDoesNotThrow(()->bankLoanJob.bankLoanPaymentJob());

    }


}