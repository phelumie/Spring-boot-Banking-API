package com.microfinanceBank.Employee.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetails implements Serializable {

    @Id
    @Column(unique = true)
    private Long emp_id;
    private Date dob;
    private String favouriteQuotes;
    private String hobby;
}
