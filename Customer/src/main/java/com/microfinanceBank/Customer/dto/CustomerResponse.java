package com.microfinanceBank.Customer.dto;
import com.microfinanceBank.Customer.entity.Account;
import lombok.Data;

@Data
public class CustomerResponse {
    private Long id;
    private String name;
    private String email;
    private String contactNumber;
    private Account account;
    private String imageUrl;


}
