package com.shp.fms.controller.response;

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
	
	public CommonResponse failExceptionResponse(String message) {
		CommonResponse response = new CommonResponse();
		response.setCode(-1);
		response.setMessage(message);
		return response;
	}
}
