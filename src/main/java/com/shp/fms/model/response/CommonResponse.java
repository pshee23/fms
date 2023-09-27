package com.shp.fms.model.response;

import lombok.Data;

@Data
public class CommonResponse {
	int code;
	String message;
	
	public CommonResponse successResponse() {
		CommonResponse response = new CommonResponse();
		response.setCode(200000);
		response.setMessage("success");
		return response;
	}
	
	public CommonResponse failResponse() {
		CommonResponse response = new CommonResponse();
		response.setCode(200500);
		response.setMessage("fail");
		return response;
	}
	
	public CommonResponse failResponse(int code, String message) {
		CommonResponse response = new CommonResponse();
		response.setCode(code);
		response.setMessage(message);
		return response;
	}
	
	public CommonResponse failExceptionResponse(String message) {
		CommonResponse response = new CommonResponse();
		response.setCode(500000);
		response.setMessage(message);
		return response;
	}
	
	public CommonResponse failJpaViolationResponse(String message) {
		CommonResponse response = new CommonResponse();
		response.setCode(400100);
		response.setMessage(message);
		return response;
	}
	
}
