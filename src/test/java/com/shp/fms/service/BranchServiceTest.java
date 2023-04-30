package com.shp.fms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shp.fms.controller.request.BranchRequestBody;
import com.shp.fms.model.BranchInfo;
import com.shp.fms.model.entity.Branch;
import com.shp.fms.repository.BranchRepository;
import com.shp.fms.repository.mapper.BranchMapper;

@ExtendWith(MockitoExtension.class)
class BranchServiceTest {

	@Mock
	BranchRepository branchRepository;
	
	@Mock
	BranchMapper branchMapper;
	
	@InjectMocks
	BranchService branchService;
	
	@Test
	void testRegisterBranch() {
		BranchRequestBody requestBody = firstRequestBody();
		Branch firstBranch = makeBranch(requestBody);
		BranchInfo firstBranchInfo = makeBranchInfo(firstBranch);
		
		// import static org.mockito.Mockito.*; 가 있어야 when 사용가능
		when(branchMapper.mapToBranch(requestBody)).thenReturn(firstBranch);
		firstBranch.setBranchId(1l);
		when(branchRepository.save(firstBranch)).thenReturn(firstBranch);
		when(branchMapper.mapToBranchInfo(firstBranch)).thenReturn(firstBranchInfo);
		
		BranchInfo returnInfo = branchService.registerBranch(requestBody);
		
		assertEquals(firstBranchInfo, returnInfo);
	}

	@Test
	void testModifyBranch() {
		long dummyBranchId = 1l;
		BranchRequestBody requestBody = firstRequestBody();
		Branch firstBranch = makeBranch(requestBody);
		Branch firstReturnedBranch = makeReturnedBranch(requestBody);
		BranchInfo firstBranchInfo = makeBranchInfo(firstBranch);
		Optional<Branch> opBranch = Optional.of(firstReturnedBranch);
		
		when(branchRepository.findById(dummyBranchId)).thenReturn(opBranch);
		when(branchMapper.mapToBranch(opBranch.get(), requestBody)).thenReturn(firstReturnedBranch);
		when(branchRepository.save(firstReturnedBranch)).thenReturn(firstReturnedBranch);
		when(branchMapper.mapToBranchInfo(firstReturnedBranch)).thenReturn(firstBranchInfo);
		
		BranchInfo returnInfo = branchService.modifyBranch(dummyBranchId, requestBody);
		
		assertEquals(firstBranchInfo, returnInfo);
	}

	@Test
	void testDeleteBranch() {
		long dummyBranchId = 1l;
		
		when(branchRepository.existsById(any())).thenReturn(true);
		
		boolean isDelete = branchService.deleteBranch(dummyBranchId);
		
		assertEquals(false, isDelete);
	}

	@Test
	void testGetAllBranchInfo() {
		BranchRequestBody requestBody = firstRequestBody();
		Branch firstBranch = makeBranch(requestBody);
		BranchInfo firstBranchInfo = makeBranchInfo(firstBranch);
		List<Branch> branchList = new ArrayList<>();
		branchList.add(firstBranch);
		List<BranchInfo> branchInfoList = new ArrayList<>();
		branchInfoList.add(firstBranchInfo);
		
		when(branchRepository.findAll()).thenReturn(branchList);
		when(branchMapper.mapToBranchInfoList(branchList)).thenReturn(branchInfoList);
		
		List<BranchInfo> returnList = branchService.getAllBranchInfo();
		
		assertEquals(branchInfoList, returnList);
	}

	@Test
	void testGetBranchById() {
		Long dummyId = 1L;
		BranchRequestBody requestBody = firstRequestBody();
		Branch firstReturnedBranch = makeReturnedBranch(requestBody);
		Optional<Branch> opBranch = Optional.of(firstReturnedBranch);
		
		when(branchRepository.findById(dummyId)).thenReturn(opBranch);
		
		Branch returnBranch = branchService.getBranchById(dummyId);
		
		assertEquals(opBranch.get(), returnBranch);
	}

	@Test
	void testGetBranchInfoById() {
		Long dummyId = 1L;
		BranchRequestBody requestBody = firstRequestBody();
		Branch firstReturnedBranch = makeReturnedBranch(requestBody);
		Optional<Branch> opBranch = Optional.of(firstReturnedBranch);
		BranchInfo firstBranchInfo = makeBranchInfo(opBranch.get());
		
		when(branchRepository.findById(dummyId)).thenReturn(opBranch);
		when(branchMapper.mapToBranchInfo(opBranch.get())).thenReturn(firstBranchInfo);
		
		BranchInfo returnBranchInfo = branchService.getBranchInfoById(dummyId);
		
		assertEquals(firstBranchInfo, returnBranchInfo);
	}

	
	private BranchRequestBody firstRequestBody() {
		BranchRequestBody requestBody = new BranchRequestBody();
		requestBody.setName("firstName");
		requestBody.setAddress("firstAddress");
		requestBody.setPhoneNumber("firstPhoneNumber");
		return requestBody;
	}
	
	private Branch makeBranch(BranchRequestBody requestBody) {
		Branch branch = new Branch();
		branch.setName(requestBody.getName());
		branch.setAddress(requestBody.getAddress());
		branch.setPhoneNumber(requestBody.getPhoneNumber());
		return branch;
	}
	
	private Branch makeReturnedBranch(BranchRequestBody requestBody) {
		Branch branch = new Branch();
		branch.setBranchId(1L);
		branch.setName(requestBody.getName());
		branch.setAddress(requestBody.getAddress());
		branch.setPhoneNumber(requestBody.getPhoneNumber());
		return branch;
	}
	
	private BranchInfo makeBranchInfo(Branch branch) {
		return BranchInfo.builder()
				.branchId(1l)
				.name(branch.getName())
				.address(branch.getAddress())
				.phoneNumber(branch.getPhoneNumber())
				.build();
	}

}
