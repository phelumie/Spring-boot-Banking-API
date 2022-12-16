package com.microfinanceBank.Customer.email;

import com.microfinanceBank.Customer.dto.CustomerDto;
import com.microfinanceBank.Customer.entity.Customer;
import com.microfinanceBank.Customer.service.Impl.Emailservice;
import com.sendgrid.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailQueue {
    private final Emailservice emailservice;

    @RabbitListener(queues = "welcomeMailQueue")
    public void welcomeMail(CustomerDto customerDto){
        log.info("Send welcome mail to ->{}", customerDto.getEmail());
        Response response = getWelcomeMailResponse(customerDto);
        System.out.println("emailservice "+response.getStatusCode());
        if(response.getStatusCode()==200||response.getStatusCode()==202)
            log.info("Account creation email was sent to "+ customerDto.getEmail());


    }


    public void birthdayMail(Customer customer){
        log.info("Send happy birthday mail to ->{}",customer);
        Response response = happyBirthDayMail(customer);
        if(response.getStatusCode()==200||response.getStatusCode()==202)
            log.info("Happy birthday  email was sent to -> "+customer.getEmail());

    }

    private Response happyBirthDayMail(Customer customer) {
        String name=customer.getFirstName().concat(" ").concat(customer.getLastName());
        StringBuilder message=new StringBuilder();
        message.append("<h5>").append("Hurray!!! Happy Birthday ")
                .append(name).append(".Many more years to come dear");
        message.append("<h6>").append("May lines begin to fall for you in pleasant places as from ")
                .append(customer.getCustomerDetails().getDob()).append(".Enjoy your day!!");

        EmailRequest emailRequest=new EmailRequest(customer.getEmail(),"Hurray!!!",message.toString() );
        com.sendgrid.Response response=emailservice.sendEmail(emailRequest);
        return response;
    }

    private com.sendgrid.Response getWelcomeMailResponse(CustomerDto customer) {
        EmailRequest emailRequest=new EmailRequest(customer.getEmail(),"Welcome","Account Successfully created");
        com.sendgrid.Response response=emailservice.sendEmail(emailRequest);
        return response;
    }
}
