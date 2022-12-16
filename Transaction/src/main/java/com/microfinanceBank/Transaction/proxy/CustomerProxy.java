package com.microfinanceBank.Transaction.proxy;

import com.microfinanceBank.Transaction.Config.FeignClientInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@FeignClient(name = "customer",configuration = FeignClientInterceptor.class)
//@LoadBalancerClient(name = "customer",configuration= LoadBalancerConfiguration.class)
public interface CustomerProxy {
//    @GetMapping("api/customer/{accountNumber}")
//    public ResponseEntity<Optional<Customer>> getCustomerByAccountNumber(@PathVariable Long accountNumber);


    }