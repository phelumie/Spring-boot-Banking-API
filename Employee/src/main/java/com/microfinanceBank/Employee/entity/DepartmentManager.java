package com.microfinanceBank.Employee.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "dept_manager")
@NoArgsConstructor
@Data
public class DepartmentManager implements Serializable {
    @Id
    @Column(name = "emp_id",nullable = false)
    private Long empId;

    @OneToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "dept_no")
    private Department deptNo;
    @OneToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "branch_id")
    private Branch branchId;

    @CreationTimestamp
    @Column(name = "from_date",nullable = false)
    private Date date;
}
