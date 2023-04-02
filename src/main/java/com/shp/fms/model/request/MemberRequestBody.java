package com.shp.fms.model.request;

import lombok.Data;

@Data
public class MemberRequestBody {
	private long branchId;
	private long employeeId;
	private String name;
	private String address;
	private String phoneNumber;
	private String loginId;
	private String loginPw;
}
