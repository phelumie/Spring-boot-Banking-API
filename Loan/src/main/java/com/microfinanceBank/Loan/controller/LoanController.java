package com.microfinanceBank.Loan.controller;

import com.microfinanceBank.Loan.cronjobs.BankLoanJob;
import com.microfinanceBank.Loan.dto.LoanRequest;
import com.microfinanceBank.Loan.dto.LoanRequestResponse;
import com.microfinanceBank.Loan.service.LoanService;
import com.microfinanceBank.Loan.service.P2pService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class LoanController {

    private final LoanService loanService;
    private  final P2pService p2pService;

    @PostMapping("bank-loan")
    public ResponseEntity<LoanRequestResponse> BankLoanRequest(@Valid @RequestBody LoanRequest loanRequest){
        var response=loanService.loanRequest(loanRequest);

        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }

    @PostMapping("p2p-loan")
    public ResponseEntity P2pRequestLoan(@Valid @RequestBody LoanRequest loanRequest){
        p2pService.loanRequest(loanRequest);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("approve-loan")
    public ResponseEntity approveLoans(@RequestParam("loan-id") String id){
        loanService.approveLoan(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
