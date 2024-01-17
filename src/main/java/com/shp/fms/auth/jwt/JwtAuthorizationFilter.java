//package com.shp.fms.auth.jwt;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Optional;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.shp.fms.auth.Login;
//import com.shp.fms.auth.auth.PrincipalDetails;
//import com.shp.fms.model.entity.Employee;
//import com.shp.fms.model.entity.Member;
//import com.shp.fms.model.response.CommonResponse;
//import com.shp.fms.repository.MemberRepository;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
//	private MemberRepository memberRepository;
//	private JwtTokenProvider jwtTokenProvider;
//	
//	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
//		super(authenticationManager);
//		this.memberRepository = memberRepository;
//		this.jwtTokenProvider = jwtTokenProvider;
//	}
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//			throws IOException, ServletException {	
//		log.info("[JwtAuthorizationFilter] 인증이나 권한이 필요한 주소 요청이 들어옴");
//		
//		String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);
//		log.info("[JwtAuthorizationFilter] Jwt header={}", jwtHeader);
//		
//		if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
//			log.info("[JwtAuthorizationFilter] jwtHeader is null or not start with prefix. header={}", jwtHeader);
//			chain.doFilter(request, response);
//			return;
//		}
//		
//		if (!request.getRequestURI().equals("/api/refresh")) { 
//			log.info("[JwtAuthorizationFilter] [{}] validate token.", request.getRequestURI());
//			// 2) 토큰 검증
//			String authHeader = request.getHeader(JwtProperties.HEADER_STRING);
//			String jwtToken = authHeader.replace(JwtProperties.TOKEN_PREFIX, "");
//
//			String username = jwtTokenProvider.getUsername(jwtToken);
//			
//			// 서명이 정상적으로 됨
//			if(username != null && !username.isEmpty()) {
//				Optional<Member> memberOp = memberRepository.findByLoginId(username);
//				if(memberOp.isEmpty()) {
//					log.error("Member is null. username={}", username);
//				}
//				Login userEntity = new Login();
//				Member member = memberOp.get();
//				userEntity.setId(member.getMemberId());
//				userEntity.setUsername(member.getLoginId());
//				userEntity.setPassword(member.getLoginPw());
//				userEntity.setRoles(member.getRole());
//				
//				PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
//				
//				Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
//				
//				SecurityContextHolder.getContext().setAuthentication(authentication);
//				
//			}
//		}
//		log.info("[JwtAuthorizationFilter] end");
//		chain.doFilter(request, response);
//	}
//	
//	private UserDetails makeEntity(Employee employee) {
//		Login userEntity = new Login();
//		userEntity.setId(employee.getEmployeeId());
//		userEntity.setUsername(employee.getLoginId());
//		userEntity.setPassword(employee.getLoginPw());
//		userEntity.setRoles(employee.getRole());
//		
//		return makePrincipalDetails(userEntity);
//	}
//	
//	private UserDetails makeEntity(Member member) {
//		Login userEntity = new Login();
//		userEntity.setId(member.getMemberId());
//		userEntity.setUsername(member.getLoginId());
//		userEntity.setPassword(member.getLoginPw());
//		userEntity.setRoles(member.getRole());
//		
//		return makePrincipalDetails(userEntity);
//	}
//	
//	private UserDetails makePrincipalDetails(Login entity) {
//		log.info("[loadUserByUsername] 종료");
//		return new PrincipalDetails(entity);
//	}
//}
