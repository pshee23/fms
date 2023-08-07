package com.shp.fms.auth;

import org.springframework.stereotype.Service;

import com.shp.fms.service.MemberService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	
	private final MemberService memberService;
	
	public void login() {

	}
	
	public void signUp(SignUpRequestBody requestBody) {
		memberService.isMemberExist(requestBody.getLoginId());
	}
}
