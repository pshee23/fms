package com.shp.fms.auth.jwt;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.shp.fms.auth.Login;
import com.shp.fms.auth.auth.PrincipalDetails;
import com.shp.fms.model.entity.Member;
import com.shp.fms.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	private MemberRepository memberRepository;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
		super(authenticationManager);
		this.memberRepository = memberRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {	
		log.info("인증이나 권한이 필요한 주소 요청이 들어옴");
		
		String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);
		log.info("Jwt header = " + jwtHeader);
		
		if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		
		if (!request.getRequestURI().equals("/api/refresh")) { 
			// 2) 토큰 검증
			String authHeader = request.getHeader(JwtProperties.HEADER_STRING);
			String jwtToken = authHeader.replace(JwtProperties.TOKEN_PREFIX, "");
			String username = JWT
					.require(Algorithm.HMAC512(JwtProperties.SECRET)).build()
					.verify(jwtToken) // 여기서 Token expired check
					.getClaim("username").asString();
			
//			// TODO 로그인 정보 확인
//			if(!authRepository.isTokenValid(username, authHeader)) {
//				log.error("login info not exists!! username={}, auth={}", username, authHeader);
//				chain.doFilter(request, response);
//				return;
//			}
			
			
			// 서명이 정상적으로 됨
			if(username != null) {
				Optional<Member> memberOp = memberRepository.findByLoginId(username);
				if(memberOp.isEmpty()) {
					log.error("Member is null. username={}", username);
				}
				Login userEntity = new Login();
				Member member = memberOp.get();
				userEntity.setId(member.getMemberId());
				userEntity.setUsername(member.getLoginId());
				userEntity.setPassword(member.getLoginPw());
				userEntity.setRoles(member.getRole());
				
				PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
				
				Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}
		}
		chain.doFilter(request, response);
	}
}
