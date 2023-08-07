package com.shp.fms.auth;

import lombok.Data;

@Data
public class SignUpRequestBody {
	private long branchId;
	private long employeeId;
	private String name;
	private String address;
	private String phoneNumber;
	private String loginId;
	private String loginPw;
	private String role;
}
