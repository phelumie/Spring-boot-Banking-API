package com.microfinanceBank.Customer.controller;

import com.microfinanceBank.Customer.Config.filters.annotation.XssFilter;
import com.microfinanceBank.Customer.dto.AccountDto;
import com.microfinanceBank.Customer.dto.CardRequest;
import com.microfinanceBank.Customer.dto.CustomerDto;
import com.microfinanceBank.Customer.enums.AccountType;
import com.microfinanceBank.Customer.service.AccountService;
import com.microfinanceBank.Customer.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("card-request")
<<<<<<< HEAD
    @Operation(summary = "Card request",description = "Requesting for debit card",tags = "Post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Card Request was successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDto.class))})})
//    @XssFilter
    public ResponseEntity requestCard(@Valid @RequestBody CardRequest cardRequest){
=======
    @Operation(summary = "Create more Account",description = "Create more Accounts for existing customers",tags = "Post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Account created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDto.class))})})
    @XssFilter
    public ResponseEntity createAccount(@Valid @RequestBody CardRequest cardRequest){
>>>>>>> 3042050908729fcb60132c5fbfdbb6f52055d03b
        cardService.cardRequest(cardRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
