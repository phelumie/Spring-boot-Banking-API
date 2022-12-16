package com.microfinanceBank.Customer.customConstraints;

import com.microfinanceBank.Customer.repository.CustomerRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    CustomerRepository customerRepository;

    HikariDataSource hikariDataSource;
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        return !customerRepository.existsByEmail(email);
    }
}
