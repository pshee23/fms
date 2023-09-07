package com.shp.fms.auth.jwt;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.shp.fms.auth.TokenInfo;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
	
	private final Key key;
	
    public JwtTokenProvider(@Value("${app.jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
	
	//Authentication 을 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public TokenInfo generateToken(Authentication authentication) {
        return generateToken(authentication.getName(), authentication.getAuthorities());
    }
    
  //name, authorities 를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public TokenInfo generateToken(String name, Collection<? extends GrantedAuthority> inputAuthorities) {
    	log.info("generate Token. name={}", name);
        //권한 가져오기
        String authorities = inputAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();

        //Generate AccessToken
        String accessToken = Jwts.builder()
                .setSubject(name)
                .setIssuedAt(now)   //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + JwtProperties.ACCESS_TOKEN_EXPIRE_TIME))  //토큰 만료 시간 설정
                .claim(JwtProperties.SECRET, authorities)
                .claim("type", JwtProperties.TYPE_ACCESS)
                .claim("username", name)
                .signWith(key, SignatureAlgorithm.HS512).compact();
//		String accessToken = JWT.create()
//			.withSubject(name)
//			.withIssuedAt(now)
//			.withExpiresAt(new Date(now.getTime() + JwtProperties.ACCESS_TOKEN_EXPIRE_TIME))
//			.withClaim(JwtProperties.SECRET, authorities)
//			.withClaim("type", JwtProperties.TYPE_ACCESS)
//			.withClaim("username", name)
//			.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		log.info("generate accessToken. accessToken={}", accessToken);
		
        //Generate RefreshToken
        String refreshToken = Jwts.builder()
        		.setSubject(name)
                .setIssuedAt(now)   //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + JwtProperties.REFRESH_TOKEN_EXPIRE_TIME)) //토큰 만료 시간 설정
                .claim("type", JwtProperties.TYPE_REFRESH)
                .claim("username", name)
                .signWith(key, SignatureAlgorithm.HS512).compact();
//		String refreshToken = JWT.create()
//				.withSubject(name)
//				.withIssuedAt(now)
//				.withExpiresAt(new Date(now.getTime() + JwtProperties.REFRESH_TOKEN_EXPIRE_TIME))
//				.withClaim("type", JwtProperties.TYPE_REFRESH)
//				.withClaim("username", name)
//				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		log.info("generate refreshToken. refreshToken={}", refreshToken);

        return TokenInfo.builder()
                .grantType(JwtProperties.TOKEN_PREFIX)
                .accessToken(accessToken)
                .accessTokenExpirationTime(JwtProperties.ACCESS_TOKEN_EXPIRE_TIME)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(JwtProperties.REFRESH_TOKEN_EXPIRE_TIME)
                .build();
    }
    
    public String getUsername(String token) {
		return (String) Jwts
            	.parserBuilder()
            	.setSigningKey(key)
            	.build()
            	.parseClaimsJws(token)
            	.getBody()
        		.get("username");
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts
            	.parserBuilder()
            	.setSigningKey(key)
            	.build()
            	.parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }
    
    public boolean isRefreshToken(String token) {
        String type = (String) Jwts.parserBuilder()
        		.setSigningKey(key)
        		.build()
        		.parseClaimsJws(token)
        		.getBody()
        		.get("type");
        return type.equals(JwtProperties.TYPE_REFRESH);
    }
    
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtProperties.HEADER_STRING);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return bearerToken.replace(JwtProperties.TOKEN_PREFIX, "");
        }
        return null;
    }
}
