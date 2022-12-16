//package com.microfinanceBank.Transaction.customContraints;
//
//import com.microfinanceBank.Customer.repository.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
//@Component
//public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
//
//    @Autowired
//    CustomerRepository customerRepository;
//
//    @Override
//    public boolean isValid(String email, ConstraintValidatorContext context) {
//        return customerRepository.findByEmail(email).isEmpty();
//    }
//}
