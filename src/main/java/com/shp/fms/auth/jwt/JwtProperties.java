package com.shp.fms.auth.jwt;

public interface JwtProperties {
	String SECRET = "toyProject1234"; // 서버만 알고 있는 비밀값
	int EXPIRATION_TIME = 60000 * 10 * 10;
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
}
