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
import com.shpfms.service.MemberService;

import lombok.AllArgsConstructor;

@RequestMapping("/member")
@RestController
@AllArgsConstructor
public class MemberController {

	private final MemberService memberService;
	
	// Create New Member
	@PostMapping
	public ResponseEntity<String> registerMember(@RequestBody MemberRequestBody requestBody) {
		MemberInfo memberInfo = memberService.registerMember(requestBody);
		return ResponseEntity.ok().build();
	}
	
	// Modify Exist Member
	@PutMapping
	public ResponseEntity<String> modifyMember(
			@PathVariable long memberId,
			@RequestBody MemberRequestBody requestBody) {
		MemberInfo memeberInfo = memberService.modifyMember(memberId, requestBody);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{memberId}")
	public ResponseEntity<String> deleteMember(@PathVariable long memberId) {
		boolean deleteSuccess = memberService.deleteMember(memberId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/list")
	public ResponseEntity<String> getAllMember() {
		List<MemberInfo> memberInfoList = memberService.getAllMember();
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/list/{memeberId}")
	public ResponseEntity<String> getMemberById(@PathVariable long memberId) {
		MemberInfo memberInfo = memberService.getMemberById(memberId);
		return ResponseEntity.ok().build();
	}
	
}
