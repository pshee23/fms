package com.shp.fms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shp.fms.model.BranchInfo;
import com.shp.fms.model.entity.Branch;
import com.shp.fms.model.request.BranchRequestBody;
import com.shp.fms.repository.adapter.BranchPersistenceAdapter;
import com.shp.fms.repository.mapper.BranchMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BranchService {

	private final BranchPersistenceAdapter branchAdapter;
	
	private final BranchMapper branchMapper;
	
	public BranchInfo registerBranch(BranchRequestBody registerInfo) {
		Branch branch = branchMapper.mapToBranch(registerInfo);
		branch = branchAdapter.saveBranch(branch);
		return branchMapper.mapToBranchInfo(branch);
	}
	
	// TODO custom Exception
	public BranchInfo modifyBranch(long branchId, BranchRequestBody modifyInfo) {
		Branch branch = branchAdapter.findBranchInfoById(branchId);
		branch = branchMapper.mapToBranch(branch, modifyInfo);
		branch = branchAdapter.saveBranch(branch);
		return branchMapper.mapToBranchInfo(branch);
	}
	
	// TODO find a safe and right way to delete data
	public boolean deleteBranch(long branchId) {
		branchAdapter.deleteBranch(branchId);
		if(branchAdapter.isBranchExist(branchId)) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<BranchInfo> getAllBranch() {
		List<Branch> branchList = branchAdapter.findAllBranch();
		return branchMapper.mapToBranchInfoList(branchList);
	}
	
	public BranchInfo getBranchById(long branchId) {
		Branch branch = branchAdapter.findBranchInfoById(branchId);
		return branchMapper.mapToBranchInfo(branch);
	}
}
