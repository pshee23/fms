package com.shp.fms.auth;

import lombok.Data;

@Data
public class LoginRequestBody {
	private String username;
	private String password;
}
