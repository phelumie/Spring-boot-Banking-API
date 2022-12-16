package com.microfinanceBank.Customer.service;

import com.microfinanceBank.Customer.dto.CustomerDto;
import com.microfinanceBank.Customer.dto.Register;

import java.util.List;

public interface CustomerService {

    CustomerDto createCustomer(Register register);

    List<CustomerDto> getAllCustomer();
    CustomerDto updateCustomerDetails(CustomerDto customerDto);
    String getCurrentUserLogin();

    void deleteCustomer(long id, String keycloakId);

    CustomerDto getCustomerByKeycloakId();
    void logout();

}