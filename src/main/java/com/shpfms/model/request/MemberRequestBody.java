package com.shpfms.model.request;

import lombok.Data;

@Data
public class MemberRequestBody {
	private long branchId;
	private String name;
	private String address;
	private String phoneNumber;
	private String loginId;
	private String loginPw;
}
