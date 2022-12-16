package com.microfinanceBank.Employee.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.microfinanceBank.Employee.enums.Gender;
import com.microfinanceBank.Employee.enums.Role;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.keycloak.representations.idm.AdminEventRepresentation;
import org.keycloak.representations.idm.EventRepresentation;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
//import java.util.Collections;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false,name = "emp_id",unique = true)
    private Long id;

    @Column(updatable = false,unique = true,nullable = false)
    private String keycloakId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @ManyToOne(cascade ={
            CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH
    },fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id")
    @JsonBackReference
    private Branch branch;
    @Column(unique = true, nullable = false)
    private String email;
    @JoinColumn(name = "emp_details",unique = true)
    @OneToOne(cascade = CascadeType.ALL)
    private EmployeeDetails employeeDetails;
    @ManyToOne
    @JoinColumn(name = "dept_no")
    private Department dept;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String profileImageUrl;
    private Date lastLoginDate;
    private Date lastLoginDateDisplay;
    @CreationTimestamp
    private Date joinDate;
    private boolean isActive;
    private boolean isNotLocked;

    public void addBranch(Branch branch){
        if (branch !=null){
            this.branch=branch;
            branch.setEmployees(Collections.singletonList(this));
        }
    }
}

