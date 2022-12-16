package com.microfinanceBank.Customer.dto;

import com.microfinanceBank.Customer.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;

}
