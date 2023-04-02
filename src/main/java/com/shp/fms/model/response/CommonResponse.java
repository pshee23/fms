package com.shp.fms.model.response;

import lombok.Data;

@Data
public class CommonResponse {
	int code;
	String message;
	
	public CommonResponse successResponse() {
		CommonResponse response = new CommonResponse();
		response.setCode(1);
		response.setMessage("success");
		return response;
	}
	
	public CommonResponse failResponse() {
		CommonResponse response = new CommonResponse();
		response.setCode(-1);
		response.setMessage("fail");
		return response;
	}
}
