package com.microfinanceBank.Employee.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class LoginDto {

    @NotNull
    private String password;
    @Email(message = "Please enter a valid email address")
    @NotNull
    private String email;

}
