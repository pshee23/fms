package com.shpfms.controller;

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

import com.shpfms.model.MemberInfo;
import com.shpfms.model.request.MemberRequestBody;
import com.shpfms.model.response.MemberResponse;
import com.shpfms.service.MemberService;

import lombok.AllArgsConstructor;

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
	
	@GetMapping("/list")
	public ResponseEntity<MemberResponse> getAllMember() {
		List<MemberInfo> memberInfoList = memberService.getAllMember();
		return ResponseEntity.ok(new MemberResponse().successResponse(memberInfoList));
	}
	
	@GetMapping("/list/{memberId}")
	public ResponseEntity<MemberResponse> getMemberById(@PathVariable long memberId) {
		MemberInfo memberInfo = memberService.getMemberById(memberId);
		return ResponseEntity.ok(new MemberResponse().successResponse(memberInfo));
	}
	
}
