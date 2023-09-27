package com.shp.fms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shp.fms.common.exception.NoDataReturnedException;
import com.shp.fms.common.exception.NoResultByIdException;
import com.shp.fms.common.type.ServiceType;
import com.shp.fms.model.BranchInfo;
import com.shp.fms.model.entity.Branch;
import com.shp.fms.model.request.BranchRequestBody;
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
	
	public BranchInfo modifyBranch(long branchId, BranchRequestBody modifyInfo) {
		Branch branch = getBranchById(branchId);
		Branch modifiedBranch = branchMapper.mapToBranch(branch, modifyInfo);
		modifiedBranch = branchRepository.save(modifiedBranch);
		return branchMapper.mapToBranchInfo(modifiedBranch);
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
		if(branchList.isEmpty()) {
			throw new NoDataReturnedException(ServiceType.BRANCH.getName());
		}
		return branchMapper.mapToBranchInfoList(branchList);
	}
	
	public Branch getBranchById(long branchId) {
		Optional<Branch> branch = branchRepository.findById(branchId);
		if(branch.isEmpty()) {
			throw new NoResultByIdException(branchId, ServiceType.BRANCH.getName());
		}
		return branch.get();
	}
	
	public BranchInfo getBranchInfoById(long branchId) {
		return branchMapper.mapToBranchInfo(getBranchById(branchId));
	}
}
