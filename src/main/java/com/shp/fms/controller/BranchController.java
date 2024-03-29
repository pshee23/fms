package com.shp.fms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shp.fms.model.BranchInfo;
import com.shp.fms.model.request.BranchRequestBody;
import com.shp.fms.model.response.BranchResponse;
import com.shp.fms.service.BranchService;

import lombok.AllArgsConstructor;

@RequestMapping("/branch")
@RestController
@AllArgsConstructor
public class BranchController {
	
	private final BranchService branchService;
	
	// Create New Branch
	@PostMapping
	public ResponseEntity<BranchResponse> registerBranch(@RequestBody BranchRequestBody requestBody) {
		BranchInfo branchInfo = branchService.registerBranch(requestBody);
		return ResponseEntity.ok(new BranchResponse().successResponse(branchInfo));
	}
	
	// Modify Exist Branch
	@PutMapping("/{branchId}")
	public ResponseEntity<BranchResponse> modifyBranch(
			@PathVariable long branchId,
			@RequestBody BranchRequestBody requestBody) {
		BranchInfo branchInfo = branchService.modifyBranch(branchId, requestBody);
		return ResponseEntity.ok(new BranchResponse().successResponse(branchInfo));
	}
	
	@DeleteMapping("/{branchId}")
	public ResponseEntity<BranchResponse> deleteBranch(@PathVariable long branchId) {
		boolean isDeleted = branchService.deleteBranch(branchId);
		return ResponseEntity.ok(new BranchResponse().deleteResponse(isDeleted));	
	}
	
	// TODO list by 5
	@GetMapping("/list")
	public ResponseEntity<BranchResponse> getAllBranch() {
		List<BranchInfo> branchInfoList = branchService.getAllBranchInfo();
		return ResponseEntity.ok(new BranchResponse().successResponse(branchInfoList));
	}
	
	@GetMapping("/list/{branchId}")
	public ResponseEntity<BranchResponse> getBranchById(@PathVariable long branchId) {
		BranchInfo branchInfo = branchService.getBranchInfoById(branchId);
		return ResponseEntity.ok(new BranchResponse().successResponse(branchInfo));
	}
}
