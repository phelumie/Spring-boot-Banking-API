package com.microfinanceBank.Loan.projections;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;

public interface pppp {


    Long id();
    BigDecimal paymentAmount();
    LocalDate PaymentDate();

    Time time();
}
