package com.microfinanceBank.Customer.controller;

import com.microfinanceBank.Customer.Config.filters.annotation.XssFilter;
import com.microfinanceBank.commondto.transaction.TransactionDto;
import com.microfinanceBank.commondto.transaction.TransferTransactionDto;
import com.microfinanceBank.Customer.service.BankService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/transaction")
@RequiredArgsConstructor
@Validated
@Hidden
public class BankServiceController {
    private final BankService bankService;


    @PostMapping("withdraw")
    public ResponseEntity withdraw(@Valid @RequestBody TransactionDto transaction){
        bankService.withdraw(transaction);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("transfer")
    public ResponseEntity transfer(@Valid @RequestBody TransferTransactionDto transfer){
        bankService.transfer(transfer);
        return new ResponseEntity(HttpStatus.OK);
    }
}
