package com.shp.fms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

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
		BranchRequestBody requestBody = new BranchRequestBody();
		requestBody.setName("firstName");
		requestBody.setAddress("firstAddress");
		requestBody.setPhoneNumber("firstPhoneNumber");
		
		Branch firstBranch = new Branch();
		firstBranch.setName(requestBody.getName());
		firstBranch.setAddress(requestBody.getAddress());
		firstBranch.setPhoneNumber(requestBody.getPhoneNumber());
		
		BranchInfo firstBranchInfo = BranchInfo.builder()
				.branchId(1l)
				.name(firstBranch.getName())
				.address(firstBranch.getAddress())
				.phoneNumber(firstBranch.getPhoneNumber())
				.build();
		
		
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
		fail("Not yet implemented");
	}

	@Test
	void testDeleteBranch() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllBranchInfo() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBranchById() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBranchInfoById() {
		fail("Not yet implemented");
	}

	@Test
	void testBranchService() {
		fail("Not yet implemented");
	}

}
