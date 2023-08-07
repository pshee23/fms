package com.shp.fms.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.shp.fms.model.MemberInfo;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MemberAuthenticatorProvider implements AuthenticationProvider {

	private final MemberPrincipalDetailService memberPrincipalDetailService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String loginId = authentication.getName();
		String loginPw = (String) authentication.getCredentials();
		
		MemberPrincipalDetails memberPrincipalDetails = (MemberPrincipalDetails) memberPrincipalDetailService.loadUserByUsername(loginId);
		
		String dbPassword = memberPrincipalDetails.getPassword();
		if(!loginPw.equals(dbPassword)) {
			throw new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		
		MemberInfo member = memberPrincipalDetails.getMember();
		if(member == null) {
			throw new BadCredentialsException("사용자 정보가 없습니다.");
		}
		
		return new UsernamePasswordAuthenticationToken(memberPrincipalDetails, null, memberPrincipalDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}
