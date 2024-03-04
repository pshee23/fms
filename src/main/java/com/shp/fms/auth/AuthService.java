package com.shp.fms.auth;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.shp.fms.auth.jwt.JwtTokenProvider;
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

	private final JwtTokenProvider jwtTokenProvider;
	
	public void signUp(MemberRequestBody requestBody) {
		requestBody.setRole("ROLE_USER");
		String rawPassword = requestBody.getLoginPw();
		requestBody.setLoginPw(util.encodePassword(rawPassword));
		memberService.registerMember(requestBody);
	}
	
	public void logout(String username) {
		log.info("remove redis for logout. username={}", username);
		redisRepository.deleteById(username);
	}
	
	public ResponseEntity<?> refresh(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        log.info("refresh token={}", token);
        
        if (token != null && jwtTokenProvider.validateToken(token)) {
            if(jwtTokenProvider.isRefreshToken(token)) {
                RefreshToken refreshToken = redisRepository.findByRefreshToken(token);
                if (refreshToken != null) {
//                	MemberInfo memberInfo = memberService.getMemberInfoByLoginId(refreshToken.getId());                	
//            		UsernamePasswordAuthenticationToken athenticationToken = 
//            				new UsernamePasswordAuthenticationToken(memberInfo.getLoginId(), memberInfo.getLoginPw());
//            		log.info("UsernamePasswordAuthenticationToken={}", athenticationToken);
//            		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(athenticationToken);
//            		log.info("refreshAuthentication={}", authentication);
//            		TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
                	// TODO 임시
                	Collection<GrantedAuthority> collectors = new ArrayList<>();
                	collectors.add(()->{return "ROLE_USER";}); 

            		
            		TokenInfo tokenInfo = jwtTokenProvider.generateToken(refreshToken.getId(), collectors);
            	    log.info("tokenInfo={}", tokenInfo);
            	    // TODO refresh token 은 갱신 안하도록
            		redisRepository.save(RefreshToken.builder()
            	            .id(refreshToken.getId())
            	            .refreshToken(tokenInfo.getRefreshToken())
            	            .build());
            		
                    return ResponseEntity.ok(tokenInfo);
                    
                }
            }
        }

        return ResponseEntity.status(401).body("토큰 갱신에 실패했습니다.");
    }
}
