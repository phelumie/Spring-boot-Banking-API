package com.microfinanceBank.Employee.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microfinanceBank.Employee.entity.Branch;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AddressDto implements Serializable {


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;

}
