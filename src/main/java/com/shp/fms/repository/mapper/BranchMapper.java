package com.shp.fms.repository.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.shp.fms.model.BranchInfo;
import com.shp.fms.model.entity.Branch;
import com.shp.fms.model.request.BranchRequestBody;

@Component
public class BranchMapper {
	// XXX mapper 동작이 많은것에 대해 고민..
	
	public BranchInfo mapToBranchInfo(Branch branch) {
		return BranchInfo.builder()
				.branchId(branch.getBranchId())
				.name(branch.getName())
				.address(branch.getAddress())
				.phoneNumber(branch.getPhoneNumber())
				.build();
	}
	
	public Branch mapToBranch(BranchRequestBody registerInfo) {
		Branch branch = new Branch();
		branch.setName(registerInfo.getName());
		branch.setAddress(registerInfo.getAddress());
		branch.setPhoneNumber(registerInfo.getPhoneNumber());
		return branch;
	}
	
	public Branch mapToBranch(Branch originBranch, BranchRequestBody modifyInfo) {
		originBranch.setName(modifyInfo.getName());
		originBranch.setAddress(modifyInfo.getAddress());
		originBranch.setPhoneNumber(modifyInfo.getPhoneNumber());
		return originBranch;
	}
	
	public List<BranchInfo> mapToBranchInfoList(List<Branch> branchList) {
		List<BranchInfo> branchInfoList = new ArrayList<>();
		
		if(branchList.isEmpty()) {
			return branchInfoList;
		}
		
		for(Branch branch : branchList) {
			BranchInfo branchInfo = mapToBranchInfo(branch);
			branchInfoList.add(branchInfo);
		}
		
		return branchInfoList;
	}
}
