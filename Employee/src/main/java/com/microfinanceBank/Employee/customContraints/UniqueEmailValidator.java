package com.microfinanceBank.Employee.customContraints;

import com.microfinanceBank.Employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !employeeRepository.existsByEmail(email);
    }
}
