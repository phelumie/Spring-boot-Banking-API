package com.microfinanceBank.Employee.controller;

import com.microfinanceBank.Employee.dto.EmployeeDto;
import com.microfinanceBank.Employee.dto.RegisterDto;
import com.microfinanceBank.Employee.dto.RegisterResponse;
import com.microfinanceBank.Employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/employee")
    @RolesAllowed("hr")
    @Operation(summary = "Register employee",description = "Api available for only hr,managers and admin",tags = "Post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Employee registered successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterResponse.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized"),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden")
    })
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterDto registerDto){
        return new ResponseEntity <>(employeeService.registerEmployee(registerDto), HttpStatus.CREATED);
    }


    @DeleteMapping("employee")
    @RolesAllowed("hr")
    @Operation(summary = "Delete employee",description = "Api available for only hr,managers and admin",tags = "Delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Employee deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Void.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized"),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden")
    })
    public ResponseEntity deleteEmployee(@RequestParam("id") Long id,@RequestParam("id") String keycloakId){
        employeeService.deleteEmployee(id,keycloakId);
        return new ResponseEntity <>( HttpStatus.OK);
    }
    @PutMapping("make-admin/{id}")
    @RolesAllowed("ADMIN")
    @Operation(summary = "make admin",description = "Api available for only  admins",tags = "Put")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202",description = "Promoted Employee  successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterResponse.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized"),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden")
    })
    public ResponseEntity<RegisterResponse> makeAdmin(@PathVariable  Long id){
    return  new ResponseEntity<>( employeeService.makeAdmin(id),HttpStatus.OK);
    }

    @PutMapping("demote-admin/{id}")
    @RolesAllowed("ADMIN")
    @Operation(summary = "demote admin",description = "Api available for only admins",tags = "Put")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Demoted Admin successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterResponse.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized"),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden")
    })
    public ResponseEntity<RegisterResponse> DemoteAdminToUser(@PathVariable  Long id){
    return  new ResponseEntity<>( employeeService.demoteAdminToEmployee(id),HttpStatus.OK);
    }

    @GetMapping("all-employees-flux")
    @Operation(summary = "get all employee",description = "get all employees using flux",tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Return employee flux successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterResponse.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized"),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden")
    })
    public ResponseEntity<Flux<EmployeeDto>> getAllUsers(){
        Flux<EmployeeDto> users = employeeService.getAllUsersFlux();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * @return A list of all employees
     *
     * @response 200 A list of all employees
     */
    @GetMapping("all-employees-list")
    @Operation(summary = "get all employee",description = "get all employees using list",tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Return employee list successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterResponse.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized"),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden")
    })
    public ResponseEntity<List<EmployeeDto>> getAllUsersList(){
        List<EmployeeDto> users = employeeService.getAllEmployeeList();
        return new ResponseEntity<>(users, HttpStatus.OK);

    }


}

