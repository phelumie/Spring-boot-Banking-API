package com.microfinanceBank.Customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.microfinanceBank.Customer.controller.CustomerController;
import com.microfinanceBank.Customer.customConstraints.UniqueEmail;
import com.microfinanceBank.Customer.entity.Account;
import com.microfinanceBank.Customer.entity.CustomerDetails;
import com.microfinanceBank.Customer.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto implements Serializable{

    private Long id;
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String keycloakId;
//    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    private String password;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<Account> accounts;
    private CustomerDetailsDto customerDetails;
    private String imageUrl;
    @Email
    @NotNull
    @UniqueEmail
    private String email;
    @NotNull
    private String contactNumber;
    @NotNull
    private AddressDto address;
    private Status status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date creationDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Time time;



}