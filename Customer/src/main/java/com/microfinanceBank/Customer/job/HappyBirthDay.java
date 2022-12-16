package com.microfinanceBank.Customer.job;

import com.microfinanceBank.Customer.email.EmailQueue;
import com.microfinanceBank.Customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HappyBirthDay {


    private  final CustomerRepository customerRepository;
    private final EmailQueue emailQueue;

    @Scheduled(cron = "0 0 11 * * *")
    public void  birthdayJob(){
        customerRepository.getCustomersByBirthday()
                .forEach(emailQueue::birthdayMail);
    }
}
