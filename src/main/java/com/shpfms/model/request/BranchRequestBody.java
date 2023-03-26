package com.shpfms.model.request;

import lombok.Data;

@Data
public class BranchRequestBody {
	private String name;
	private String address;
	private String phoneNumber;
}
