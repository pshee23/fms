package com.shp.fms.controller.request;

import lombok.Data;

@Data
public class BranchRequestBody {
	private String name;
	private String address;
	private String phoneNumber;
}