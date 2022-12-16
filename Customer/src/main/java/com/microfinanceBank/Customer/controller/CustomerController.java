package com.microfinanceBank.Customer.controller;


import com.microfinanceBank.Customer.dto.CustomerDto;
import com.microfinanceBank.Customer.dto.Register;
import com.microfinanceBank.Customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;



@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class CustomerController {
	private  final CustomerService customerService;

	@PostMapping("customer")
	@Operation(summary = "Create customers",description = "Create a customer account",tags = "Post")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201",description = "Created a customer account",
			content = {@Content(mediaType = "application/json",
			schema = @Schema(implementation = CustomerDto.class))}),
			@ApiResponse(responseCode = "409",description = "Account with email address already exists",
			content = @Content)})
//	@XssFilter
	public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody Register register){
		CustomerDto savedUser = customerService.createCustomer(register);
<<<<<<< HEAD
=======
		System.out.println("webbbbbbbbbbb111111111111");
>>>>>>> 3042050908729fcb60132c5fbfdbb6f52055d03b
		return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
	}

	@GetMapping("customers")
	@RolesAllowed("ADMIN")
	@Operation(summary = "Get customers",description = "Return a list of customers",tags = "Get")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",description = "Request was a success",
					content = {@Content(mediaType = "application/json",
							schema = @Schema(implementation = CustomerDto.class))}),
			@ApiResponse(responseCode = "401",description = "Unauthorized access ",
					content = @Content)})
	@Retryable(maxAttempts = 2)
	public ResponseEntity<List<CustomerDto>> getAllCustomers(){
		List<CustomerDto> customers = customerService.getAllCustomer();
		return new ResponseEntity<>(customers, HttpStatus.OK);

	}


	@PatchMapping("customer")
	@Operation(summary = "Update customers",description = "updates a customer account",tags = "Put")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",description = "Request was successful",
					content = {@Content(mediaType = "application/json",
							schema = @Schema(implementation = CustomerDto.class))})})
//	@XssFilter
	public ResponseEntity<CustomerDto> updateCustomerDetails(@Valid @RequestBody CustomerDto customer){
		CustomerDto customerDto = customerService.updateCustomerDetails(customer);
		return new ResponseEntity<>(customerDto, HttpStatus.OK);
	}

	@DeleteMapping("customer")
	@Operation(summary = "Delete customer including all  their accounts ",description = "Delete Accounts customer profile by id ",tags = "Delete")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",description = "Profile deleted",
					content = {@Content(mediaType = "application/json",
							schema = @Schema(implementation = Void.class))})})
	public ResponseEntity deleteById(@RequestParam("id") Long id, @RequestParam("keycloakId") String keycloakId){
		customerService.deleteCustomer(id,keycloakId);
		return new ResponseEntity<>( HttpStatus.OK);
	}


//	@GetMapping("logout")
//	public ResponseEntity logout(){
//		customerService.logout();
//	return new ResponseEntity(HttpStatus.OK) ;
//	}



}

