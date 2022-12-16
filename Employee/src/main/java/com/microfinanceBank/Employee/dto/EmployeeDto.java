package com.microfinanceBank.Employee.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microfinanceBank.Employee.entity.Branch;
import com.microfinanceBank.Employee.entity.Department;
import com.microfinanceBank.Employee.enums.Gender;
import com.microfinanceBank.Employee.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class EmployeeDto  {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String keycloakId;
    private String firstName;
    private String lastName;
    private BranchDto branch;

    private String email;
    private Department dept;
    private Gender gender;
    private Role role;
    private String profileImageUrl;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date lastLoginDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date lastLoginDateDisplay;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date joinDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean isActive;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean isNotLocked;
}
