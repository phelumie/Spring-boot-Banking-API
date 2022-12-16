package com.microfinanceBank.Employee.repository;


import com.microfinanceBank.Employee.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch,Long> {

}
