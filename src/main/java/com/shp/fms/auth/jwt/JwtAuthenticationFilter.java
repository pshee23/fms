package com.shp.fms.auth.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shp.fms.auth.AuthRepository;
import com.shp.fms.auth.Login;
import com.shp.fms.auth.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final AuthRepository repository;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		log.info("[attemptAuthentication] 실행");
		
		try {
			ObjectMapper om = new ObjectMapper();
			Login loginReq = om.readValue(request.getInputStream(), Login.class);
			log.info("request loginRequestBody. body={}", loginReq);
			
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword());
			log.info("autenticationToken. token={}", authenticationToken);
			
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			log.info("authentication. auth={}", authentication);
			
			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
			log.info("login check. username={}", principalDetails.getLoginBody().getUsername());
			
			log.info("[attemptAuthentication] 종료");
			return authentication;
		} catch (StreamReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("[attemptAuthentication] 실패");
		return null;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		log.info("successfulAuthentication 실행됨. 인증 완료!");
		
		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
		
		String jwtToken = JWT.create()
				.withSubject(principalDetails.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
				.withClaim("id", principalDetails.getLoginBody().getId()) 
				.withClaim("username", principalDetails.getLoginBody().getUsername())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		
		String prefixToken = new StringBuilder().append(JwtProperties.TOKEN_PREFIX).append(jwtToken).toString();
		response.addHeader(JwtProperties.HEADER_STRING, prefixToken);
		
		// TODO 로그인 여부 저장
		log.info("jwtToken. token={}", prefixToken);
		repository.addToken(principalDetails.getLoginBody().getUsername(), prefixToken);
	}
}
