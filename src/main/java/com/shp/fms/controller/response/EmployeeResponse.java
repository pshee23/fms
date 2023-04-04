package com.shp.fms.controller.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shp.fms.model.EmployeeInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponse extends CommonResponse {
	private List<EmployeeInfo> employeeInfoList;
	private EmployeeInfo employeeInfo;
	
	public EmployeeResponse successResponse(List<EmployeeInfo> employeeInfoList) {
		EmployeeResponse employeeResponse = setResponse(true);
		employeeResponse.setEmployeeInfoList(employeeInfoList);
		return employeeResponse;
	}
	
	public EmployeeResponse successResponse(EmployeeInfo employeeInfo) {
		EmployeeResponse employeeResponse = setResponse(true);
		employeeResponse.setEmployeeInfo(employeeInfo);
		return employeeResponse;
	}
	
	public EmployeeResponse deleteResponse(boolean isDeleted) {
		EmployeeResponse employeeResponse = setResponse(isDeleted);
		if(isDeleted) {
			employeeResponse.setMessage("delete success");
		} else {
			employeeResponse.setMessage("delete fail");
		}
		return employeeResponse;
	}
	
	private EmployeeResponse setResponse(boolean isSuccess) {
		CommonResponse commonResponse = super.successResponse();
		if(!isSuccess) {
			commonResponse = super.failResponse();
		}
		
		EmployeeResponse employeeResponse = new EmployeeResponse();
		employeeResponse.setCode(commonResponse.code);
		employeeResponse.setMessage(commonResponse.message);
		return employeeResponse;
	}
}
