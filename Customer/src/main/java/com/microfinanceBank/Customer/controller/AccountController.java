package com.microfinanceBank.Customer.controller;

import com.microfinanceBank.Customer.Config.filters.annotation.XssFilter;
import com.microfinanceBank.Customer.dto.AccountDto;
import com.microfinanceBank.Customer.dto.CustomerDto;
import com.microfinanceBank.Customer.enums.AccountType;
import com.microfinanceBank.Customer.service.AccountService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("account")
    @Operation(summary = "Create more Account",description = "Create more Accounts for existing customers",tags = "Post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Account created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDto.class))})})
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestParam("customer-id") Long customerId, @RequestParam("account-type") AccountType accountType){
        AccountDto account = accountService.createAccount(accountType, customerId);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }
    @GetMapping("account")
    @Operation(summary = "Get Account",description = "Get Accounts by account number",tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Request was found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDto.class))}),
            @ApiResponse(responseCode = "404",description = "Account not found",
                    content = @Content)})
    @Retryable(maxAttempts = 2)
    public ResponseEntity<AccountDto> getCustomerByAccountNumber(@RequestParam("accountNumber") Long accountNumber){
        AccountDto account = accountService.getAccountByAccountNumber(accountNumber);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("accounts")
    @RolesAllowed("ADMIN")
    @Operation(summary = "Get Account",description = "Get all Accounts",tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Request was found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDto.class))})})
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
                var account = accountService.getAllAccounts();
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    @DeleteMapping("account")
    @Operation(summary = "Delete Account by id",description = "Delete Accounts by Id",tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Account Deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Void.class))})})
    @Retryable(maxAttempts = 1)
    public ResponseEntity deleteById(@RequestParam("id") Long id){
        accountService.deleteAccount(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }




}
