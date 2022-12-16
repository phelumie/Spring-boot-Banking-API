package com.microfinanceBank.Customer.proxy;


import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
//@FeignClient(name = "http://localhost:8081")
@FeignClient(name = "customer")
public interface RefreshActuatorProxy {
    @PostMapping("actuator/refresh")
    public void refreshActuator();


}
