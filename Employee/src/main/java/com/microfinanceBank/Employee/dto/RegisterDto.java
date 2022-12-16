package com.microfinanceBank.Employee.dto;

import com.microfinanceBank.Employee.customContraints.UniqueEmail;
import com.microfinanceBank.Employee.entity.Branch;
import com.microfinanceBank.Employee.entity.Department;
import com.microfinanceBank.Employee.enums.Gender;
import com.microfinanceBank.Employee.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDto {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private Long branch;
    @NotNull
    @Email
    @UniqueEmail
    private String email;
    private Department dept;
    @NotNull
    private Gender gender;
    @NotNull
    private Role role;
    @NotNull
    private String profileImageUrl;
    @NotNull
    private String password;



}
