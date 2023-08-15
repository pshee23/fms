package com.shp.fms.auth;

import org.springframework.stereotype.Service;

import com.shp.fms.model.request.MemberRequestBody;
import com.shp.fms.service.MemberService;
import com.shp.fms.util.BCryptUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	
	private final MemberService memberService;
	
	private final BCryptUtil util;
	
	public void login(LoginRequestBody requestBody) {
		
	}
	
	public void signUp(MemberRequestBody requestBody) {
		requestBody.setRole("ROLE_USER");
		String rawPassword = requestBody.getLoginPw();
		requestBody.setLoginPw(util.encodePassword(rawPassword));
		memberService.registerMember(requestBody);
	}
}
