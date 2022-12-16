package com.microfinanceBank.Employee.email;

import com.microfinanceBank.Employee.dto.RegisterDto;
import com.microfinanceBank.Employee.service.Emailservice;
import com.sendgrid.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class emailQueue {
    @Autowired
    private Emailservice emailservice;

    @RabbitListener(queues = "welcomeMailQueue")
    public void welcomeMail(RegisterDto user){
        log.info("Send welcome mail to ->{}",user);
        Response response = getWelcomeMailResponse(user);
        System.out.println("emailservice "+response.getStatusCode());
        if(response.getStatusCode()==200||response.getStatusCode()==202)
            log.info("Account creation email was sent to "+user.getEmail());


    }

    private Response getWelcomeMailResponse(RegisterDto user) {
        EmailRequest emailRequest=new EmailRequest(user.getEmail(),"Welcome","Account Successfully created");
        Response response=emailservice.sendemail(emailRequest);
        return response;
    }
}
