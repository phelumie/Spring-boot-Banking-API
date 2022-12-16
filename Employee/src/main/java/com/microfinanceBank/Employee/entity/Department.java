package com.microfinanceBank.Employee.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department implements Serializable {

    @Id
    @Column(nullable = false,name = "dept_no",unique = true)
    private String deptNum;
    private String deptName;


}
