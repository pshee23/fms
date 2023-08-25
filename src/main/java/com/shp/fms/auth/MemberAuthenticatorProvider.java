//package com.shp.fms.auth;
//
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import com.shp.fms.model.MemberInfo;
//import com.shp.fms.util.BCryptUtil;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//@AllArgsConstructor
//public class MemberAuthenticatorProvider implements AuthenticationProvider {
//
//	private final MemberPrincipalDetailService memberPrincipalDetailService;
//	
//	private final BCryptUtil util;
//	
//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		String loginId = authentication.getName();
//		String loginPw = (String) authentication.getCredentials();
//		log.info("authentication id={}, pw={}, body={}", loginId, loginPw, authentication.toString());
//		MemberPrincipalDetails memberPrincipalDetails = (MemberPrincipalDetails) memberPrincipalDetailService.loadUserByUsername(loginId);
//		
//		String dbPassword = memberPrincipalDetails.getPassword();
//		if(!util.isPasswordMatch(loginPw, dbPassword)) {
//			throw new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.");
//		}
//		
//		MemberInfo member = memberPrincipalDetails.getMember();
//		if(member == null) {
//			throw new BadCredentialsException("사용자 정보가 없습니다.");
//		}
//		
//		return new UsernamePasswordAuthenticationToken(memberPrincipalDetails, loginPw, memberPrincipalDetails.getAuthorities());
//	}
//
//	@Override
//	public boolean supports(Class<?> authentication) {
//		return authentication.equals(UsernamePasswordAuthenticationToken.class);
//	}
//	
//}
