package com.microfinanceBank.Employee.service.Impl;

import com.microfinanceBank.Employee.dto.AddressDto;
import com.microfinanceBank.Employee.dto.BranchDto;
import com.microfinanceBank.Employee.entity.Address;
import com.microfinanceBank.Employee.entity.Branch;
import com.microfinanceBank.Employee.repository.BranchRepository;
import com.microfinanceBank.Employee.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;

    @Override
    public BranchDto createBranch(BranchDto branchDto) {
        log.trace("entering createBranch method");
        log.debug("Creating bank branch");
        var branch=convertBranchDtoToEntity(branchDto);
        branch.addAddress(convertAddressDtoToEntity(branchDto.getAddress()));
        var saved=branchRepository.save(branch);
        log.info("Succesfully created bank branch with address {}",saved.getAddress());
        return convertBranchEntityToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchDto> getAll() {
        return branchRepository.findAll().stream().map(this::convertBranchEntityToDto)
                .collect(Collectors.toList());
    }


    private Branch convertBranchDtoToEntity(BranchDto branchDto){
        var branch=new Branch();
        branch=modelMapper.map(branchDto,Branch.class);
        return branch;
    }

    private Address convertAddressDtoToEntity(AddressDto addressDto){
        var address=new Address();
        address=modelMapper.map(addressDto,Address.class);
        return address;
    }
    private BranchDto convertBranchEntityToDto(Branch branch){
        var branchDto=new BranchDto();
        branchDto=modelMapper.map(branch,BranchDto.class);
        return branchDto;
    }
}
