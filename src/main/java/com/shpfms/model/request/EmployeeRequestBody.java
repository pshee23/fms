package com.shpfms.model.request;

import lombok.Data;

@Data
public class EmployeeRequestBody {
	private long branchId;
	private String name;
	private String address;
	private String phoneNumber;
	private String loginId;
	private String loginPw;
	private String role;
	private String status;
}
