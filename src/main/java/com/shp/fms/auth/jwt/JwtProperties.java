package com.shp.fms.auth.jwt;

public interface JwtProperties {
	String SECRET = "toyProject1234"; // 서버만 알고 있는 비밀값
	int EXPIRATION_TIME = 60000 * 10 * 10;
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
	String HEADER_REFRESH = "RefreshToken";
	
	String TYPE_ACCESS = "access";
	String TYPE_REFRESH = "refresh";
	
	// TODO config로 따로
//	long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;               //30분
//    long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;     //7일
//    long REFRESH_TOKEN_EXPIRE_TIME_FOR_REDIS = REFRESH_TOKEN_EXPIRE_TIME / 1000L;
    
    long ACCESS_TOKEN_EXPIRE_TIME = 30 * 1000L;               // 2초
    long REFRESH_TOKEN_EXPIRE_TIME =  60 * 1000L;     // 3분
    long REFRESH_TOKEN_EXPIRE_TIME_FOR_REDIS = REFRESH_TOKEN_EXPIRE_TIME / 1000L;
}
