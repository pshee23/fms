package com.shpfms.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberInfo {
	private long memberId;
	private String name;
	private String address;
	private String phoneNumber;
	private String loginId;
	private String loginPw;
}
