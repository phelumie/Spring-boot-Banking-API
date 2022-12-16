package com.microfinanceBank.Loan.controller;

import com.microfinanceBank.Loan.dto.MakePaymentDto;
import com.microfinanceBank.Loan.service.Payment;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
@SecurityRequirement(name = "Bearer Authentication")
public class MakePaymentController {

    private final Payment payment;

    @PostMapping("payment-loan")
    public ResponseEntity paymentLoan(@Valid @RequestBody MakePaymentDto makePaymentDto){
        payment.makeLoanPayment(makePaymentDto);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
