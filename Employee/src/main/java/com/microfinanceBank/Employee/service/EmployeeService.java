package com.microfinanceBank.Employee.service;

import com.microfinanceBank.Employee.dto.EmployeeDto;
import com.microfinanceBank.Employee.dto.RegisterDto;
import com.microfinanceBank.Employee.dto.RegisterResponse;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.List;

public interface EmployeeService {
//    @CachePut(key="'allCustomer'", value="allCustomers")
    RegisterResponse registerEmployee(RegisterDto user);
    String getCurrentUserLogin();
    RegisterResponse makeAdmin(Long id);
    RegisterResponse demoteAdminToEmployee(Long id);
    Flux<EmployeeDto> getAllUsersFlux();
    List<EmployeeDto> getAllEmployeeList();

    Date getEmployeeLastLoginDate(String keycloakId);

    List<Date> getAllEmployeeLoginSessions(String keycloakId);

    List<EmployeeDto> allActiveSessions();

    void deleteEmployee(Long id,String keycloakId);
}