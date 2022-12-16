package com.microfinanceBank.Employee.repository;

import com.microfinanceBank.Employee.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,String> {
}
