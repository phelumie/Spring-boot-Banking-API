package com.microfinanceBank.Customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CustomerDetailsDto  implements Serializable {

    private LocalDate dob;
    private String occupation;
    private String maritalStatus;
    private String disability;
}
