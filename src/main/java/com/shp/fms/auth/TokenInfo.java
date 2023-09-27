package com.shp.fms.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenInfo {
	private String grantType;
    private String accessToken;
    private Long accessTokenExpirationTime;
    private String refreshToken;
    private Long refreshTokenExpirationTime;
}
