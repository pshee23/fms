package com.shp.fms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shp.fms.controller.request.BranchRequestBody;
import com.shp.fms.model.BranchInfo;
import com.shp.fms.model.entity.Branch;
import com.shp.fms.repository.BranchRepository;
import com.shp.fms.repository.mapper.BranchMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BranchService {

	private final BranchRepository branchRepository;
	
	private final BranchMapper branchMapper;
		
	public BranchInfo registerBranch(BranchRequestBody registerInfo) {
		Branch branch = branchMapper.mapToBranch(registerInfo);
		branch = branchRepository.save(branch);
		return branchMapper.mapToBranchInfo(branch);
	}
	
	// TODO custom Exception
	public BranchInfo modifyBranch(long branchId, BranchRequestBody modifyInfo) {
		Optional<Branch> optionalBranch = branchRepository.findById(branchId);
		if(optionalBranch.isEmpty()) {
			// TODO throw exception
		}
		Branch branch = branchMapper.mapToBranch(optionalBranch.get(), modifyInfo);
		branch = branchRepository.save(branch);
		return branchMapper.mapToBranchInfo(branch);
	}
	
	// TODO find a safe and right way to delete data
	public boolean deleteBranch(long branchId) {
		branchRepository.deleteById(branchId);
		if(branchRepository.existsById(branchId)) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<BranchInfo> getAllBranchInfo() {
		List<Branch> branchList = branchRepository.findAll();
		return branchMapper.mapToBranchInfoList(branchList);
	}
	
	public Branch getBranchById(long branchId) {
		Optional<Branch> branch = branchRepository.findById(branchId);
		if(branch.isEmpty()) {
			// TODO throw exception
		}
		return branch.get();
	}
	
	public BranchInfo getBranchInfoById(long branchId) {
		Optional<Branch> branch = branchRepository.findById(branchId);
		if(branch.isEmpty()) {
			// TODO throw exception
		}
		return branchMapper.mapToBranchInfo(branch.get());
	}
}
