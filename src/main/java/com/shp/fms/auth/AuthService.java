package com.shp.fms.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.shp.fms.auth.auth.PrincipalDetails;
import com.shp.fms.auth.jwt.JwtProperties;
import com.shp.fms.auth.jwt.JwtTokenProvider;
import com.shp.fms.model.MemberInfo;
import com.shp.fms.model.request.MemberRequestBody;
import com.shp.fms.service.MemberService;
import com.shp.fms.util.BCryptUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {
	
	private final MemberService memberService;
	
	private final RefreshRedisRepository redisRepository;
	
	private final BCryptUtil util;
	
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	
	private final JwtTokenProvider jwtTokenProvider;
	
	private final RedisTemplate<String, String> redisTemplate;
	
	public void signUp(MemberRequestBody requestBody) {
		requestBody.setRole("ROLE_USER");
		String rawPassword = requestBody.getLoginPw();
		requestBody.setLoginPw(util.encodePassword(rawPassword));
		memberService.registerMember(requestBody);
	}
	
	public void logout(String username, String token) {
		
	}
	
	public ResponseEntity<?> refresh(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        log.info("refresh token={}", token);
        
        if (token != null && jwtTokenProvider.validateToken(token)) {
            if (jwtTokenProvider.isRefreshToken(token)) {
                RefreshToken refreshToken = redisRepository.findByRefreshToken(token);
                if (refreshToken != null) {
//                	MemberInfo memberInfo = memberService.getMemberInfoByLoginId(refreshToken.getId());                	
//            		UsernamePasswordAuthenticationToken athenticationToken = 
//            				new UsernamePasswordAuthenticationToken(memberInfo.getLoginId(), memberInfo.getLoginPw());
//            		log.info("UsernamePasswordAuthenticationToken={}", athenticationToken);
//            		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(athenticationToken);
//            		log.info("refreshAuthentication={}", authentication);
                	
                	// TODO 임시
                	Collection<GrantedAuthority> collectors = new ArrayList<>();
                	collectors.add(()->{return "ROLE_USER";}); 

            		
            		TokenInfo tokenInfo = jwtTokenProvider.generateToken(refreshToken.getId(), collectors);
            	    log.info("tokenInfo={}", tokenInfo);
            	    
            		redisRepository.save(RefreshToken.builder()
            	            .id(refreshToken.getId())
            	            .refreshToken(tokenInfo.getRefreshToken())
            	            .build());
            		
                    return ResponseEntity.ok(tokenInfo);
                    
                }
            }
        }

        return ResponseEntity.ok("토큰 갱신에 실패했습니다.");
    }
}
