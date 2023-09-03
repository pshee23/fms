package com.shp.fms.auth;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
@RedisHash(value = "refresh", timeToLive = 604800)
public class RefreshToken {
	
	private String id; // accessToken 재발급에 사용될 id

	@Indexed
	private String refreshToken;
}
