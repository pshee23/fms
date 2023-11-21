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

import com.shp.fms.model.MemberInfo;
import com.shp.fms.model.request.MemberRequestBody;
import com.shp.fms.model.response.MemberResponse;
import com.shp.fms.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/member")
@RestController
@AllArgsConstructor
public class MemberController {

	private final MemberService memberService;
	
	// Create New Member
	@PostMapping
	public ResponseEntity<MemberResponse> registerMember(@RequestBody MemberRequestBody requestBody) {
		MemberInfo memberInfo = memberService.registerMember(requestBody);
		return ResponseEntity.ok(new MemberResponse().successResponse(memberInfo));
	}
	
	// Modify Exist Member
	@PutMapping("/{memberId}")
	public ResponseEntity<MemberResponse> modifyMember(
			@PathVariable long memberId,
			@RequestBody MemberRequestBody requestBody) {
		MemberInfo memberInfo = memberService.modifyMember(memberId, requestBody);
		return ResponseEntity.ok(new MemberResponse().successResponse(memberInfo));
	}
	
	@DeleteMapping("/{memberId}")
	public ResponseEntity<MemberResponse> deleteMember(@PathVariable long memberId) {
		boolean isDeleted = memberService.deleteMember(memberId);
		return ResponseEntity.ok(new MemberResponse().deleteResponse(isDeleted));
	}
	
	@GetMapping("/{memberId}")
	public ResponseEntity<Object> getById(@PathVariable long memberId) {
		MemberInfo memberInfo = memberService.getMemberInfoById(memberId);
		return ResponseEntity.ok(memberInfo);
	}
	
	@GetMapping("/login/{loginId}")
	public ResponseEntity<Object> getByLoginId(@PathVariable String loginId) {
		Long memberId = memberService.getEmployeeIdByLoginId(loginId);
		return ResponseEntity.ok(memberId);
	}
	
	@GetMapping("/list")
	public ResponseEntity<MemberResponse> getAllMember() {
		List<MemberInfo> memberInfoList = memberService.getAllMemberInfo();
		return ResponseEntity.ok(new MemberResponse().successResponse(memberInfoList));
	}
	
	@GetMapping("/list/{memberId}")
	public ResponseEntity<MemberResponse> getMemberById(@PathVariable long memberId) {
		MemberInfo memberInfo = memberService.getMemberInfoById(memberId);
		return ResponseEntity.ok(new MemberResponse().successResponse(memberInfo));
	}
	
	@GetMapping("/employee/{employeeId}")
	public ResponseEntity<List<MemberInfo>> getMemberByEmployeeId(@PathVariable long employeeId) {
		log.info("getMemberByEmployeeId. id={}", employeeId);
		List<MemberInfo> memberInfo = memberService.getMemberByEmployeeId(employeeId);
		return ResponseEntity.ok(memberInfo);
	}
	
	@GetMapping("/branch/employee/{employeeId}")
	public ResponseEntity<List<MemberInfo>> getMemberByBranchEmployeeId(@PathVariable long employeeId) {
		log.info("getMemberByBranchEmployeeId. id={}", employeeId);
		List<MemberInfo> memberInfo = memberService.getMemberByBranchEmployeeId(employeeId);
		return ResponseEntity.ok(memberInfo);
	}
	
	@GetMapping("/list/branch/{branchId}")
	public ResponseEntity<MemberResponse> getMemberListByBranchId(@PathVariable long branchId) {
		List<MemberInfo> memberInfo = memberService.getMemberInfoListByBranchId(branchId);
		return ResponseEntity.ok(new MemberResponse().successResponse(memberInfo));
	}
}
