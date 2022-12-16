package com.microfinanceBank.Loan.entity;

import com.microfinanceBank.Loan.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class BorrowerDetails implements Serializable {
    private static final long serialVersionUID= 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String occupation;
    private String email;
    private String mobileNumber;
    private double monthlyIncome;
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
    private int children;
    private LocalDate dob;



}
