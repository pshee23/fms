package com.shp.fms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
	
	BranchService branchService;
	
	@BeforeEach
	void setUp() {
		this.branchService = new BranchService(branchRepository, branchMapper);
	}
	
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
		BranchInfo firstBranchInfo = makeBranchInfo(firstBranch);
		Optional<Branch> opBranch = Optional.of(firstBranch);
		
		when(branchRepository.findById(dummyBranchId)).thenReturn(opBranch);
		when(branchMapper.mapToBranchInfo(firstBranch)).thenReturn(firstBranchInfo);
		when(branchRepository.save(firstBranch)).thenReturn(firstBranch);
		when(branchMapper.mapToBranchInfo(firstBranch)).thenReturn(firstBranchInfo);
		
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
		Branch firstBranch = makeBranchWithFirstRequest();
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
		Branch firstBranch = makeBranchWithFirstRequest();
		Optional<Branch> opBranch = Optional.of(firstBranch);
		
		when(branchRepository.findById(dummyId)).thenReturn(opBranch);
		
		Branch returnBranch = branchService.getBranchById(dummyId);
		
		assertEquals(firstBranch, returnBranch);
	}

	@Test
	void testGetBranchInfoById() {
		Long dummyId = 1L;
		Branch firstBranch = makeBranchWithFirstRequest();
		Optional<Branch> opBranch = Optional.of(firstBranch);
		BranchInfo firstBranchInfo = makeBranchInfo(firstBranch);
		
		when(branchRepository.findById(dummyId)).thenReturn(opBranch);
		when(branchMapper.mapToBranchInfo(firstBranch)).thenReturn(firstBranchInfo);
		
		BranchInfo returnBranchInfo = branchService.getBranchInfoById(dummyId);
		
		assertEquals(firstBranch, returnBranchInfo);
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
	
	private Branch makeBranchWithFirstRequest() {
		BranchRequestBody requestBody = firstRequestBody();
		return makeBranch(requestBody);
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
