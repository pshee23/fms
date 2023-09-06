package com.shp.fms.auth.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shp.fms.auth.Login;
import com.shp.fms.auth.RefreshRedisRepository;
import com.shp.fms.auth.RefreshToken;
import com.shp.fms.auth.TokenInfo;
import com.shp.fms.auth.auth.PrincipalDetails;
import com.shp.fms.model.response.CommonResponse;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshRedisRepository redisRepository;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationManagerBuilder authenticationManagerBuilder, 
			JwtTokenProvider jwtTokenProvider, RefreshRedisRepository redisRepository) {
		super(authenticationManager);
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.redisRepository = redisRepository;
	}
	
	
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
			
			// 이 사이에 loadUserByUsername 실행됨. 아래 authenticate에서 실행되는것같음
			Authentication authentication = this.getAuthenticationManager().authenticate(authenticationToken);
			log.info("authentication. auth={}", authentication);
			
			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
			log.info("login check. username={}", principalDetails.getLoginBody().getUsername());
			
			log.info("[attemptAuthentication] 종료");
			return authentication;
		} catch (ExpiredJwtException e) {
			log.error("ExpiredJwtException!!!", e);
			CommonResponse commonRes = new CommonResponse();
			commonRes.setCode(500);
			commonRes.setMessage("Expired token!!");
			response.setStatus(500);
            try {
				response.getWriter().write(new ObjectMapper().writeValueAsString(commonRes));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				try {
					response.getWriter().flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
		} catch (StreamReadException e) {
			log.error("StreamReadException!!!", e);
		} catch (DatabindException e) {
			log.error("DatabindException!!!", e);
		} catch (IOException e) {
			log.error("IOException!!!", e);
		}
		
		log.info("[attemptAuthentication] 실패");
		return null;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		log.info("successfulAuthentication 실행됨. 인증 완료!");
		
		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
		
//		String jwtToken = JWT.create()
//				.withSubject(principalDetails.getUsername())
//				.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
//				.withClaim("id", principalDetails.getLoginBody().getId()) 
//				.withClaim("username", principalDetails.getLoginBody().getUsername())
//				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
//		
//		String prefixToken = new StringBuilder().append(JwtProperties.TOKEN_PREFIX).append(jwtToken).toString();
//		response.addHeader(JwtProperties.HEADER_STRING, prefixToken);
		
		// Refresh Token 저장
//		UsernamePasswordAuthenticationToken token = 
//				new UsernamePasswordAuthenticationToken(principalDetails.getUsername(), principalDetails.getLoginBody().getPassword());
//		log.info("UsernamePasswordAuthenticationToken={}", token);
//		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
//		log.info("refreshAuthentication={}", authentication);
		TokenInfo tokenInfo = jwtTokenProvider.generateToken(authResult);
	    log.info("tokenInfo={}", tokenInfo);
	    
		redisRepository.save(RefreshToken.builder()
	            .id(principalDetails.getUsername())
	            .refreshToken(tokenInfo.getRefreshToken())
	            .build());
		
		response.addHeader(JwtProperties.HEADER_STRING, new StringBuilder().append(JwtProperties.TOKEN_PREFIX).append(tokenInfo.getAccessToken()).toString());
		response.addHeader(JwtProperties.HEADER_REFRESH, new StringBuilder().append(JwtProperties.TOKEN_PREFIX).append(tokenInfo.getRefreshToken()).toString());

	}
}
