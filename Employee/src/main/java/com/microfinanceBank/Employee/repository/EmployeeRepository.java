package com.microfinanceBank.Employee.repository;

import com.microfinanceBank.Employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByEmail(String email);
//    Optional<User> findById(Long id);
    Employee findByKeycloakId(String id);
    boolean existsByEmail(String email);



}
