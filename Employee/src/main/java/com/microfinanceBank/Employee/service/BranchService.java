package com.microfinanceBank.Employee.service;

import com.microfinanceBank.Employee.dto.BranchDto;

import java.util.List;

public interface BranchService {

    BranchDto createBranch(BranchDto branchDto);
    List<BranchDto> getAll();

}
