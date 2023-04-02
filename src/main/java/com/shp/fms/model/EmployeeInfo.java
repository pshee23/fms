package com.shp.fms.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeInfo {
	private long employeeId;
	private long branchId;
	private String name;
	private String address;
	private String phoneNumber;
	private String loginId;
	private String loginPw;
	private String role;
	private String status;
}
