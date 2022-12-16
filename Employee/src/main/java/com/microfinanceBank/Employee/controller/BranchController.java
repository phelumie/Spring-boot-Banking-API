package com.microfinanceBank.Employee.controller;

import com.microfinanceBank.Employee.dto.BranchDto;
import com.microfinanceBank.Employee.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;

    @PostMapping("branch")
    public ResponseEntity<BranchDto> createBranch(@Valid @RequestBody BranchDto branchDto){
        var response=branchService.createBranch(branchDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("all-branch")
    public ResponseEntity<List<BranchDto>> getAllBranch(){
        var response=branchService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
