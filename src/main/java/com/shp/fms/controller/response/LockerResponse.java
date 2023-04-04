package com.shp.fms.controller.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shp.fms.model.LockerInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LockerResponse extends CommonResponse {
	private List<LockerInfo> lockerInfoList;
	private LockerInfo lockerInfo;
	
	public LockerResponse successResponse(List<LockerInfo> lockerInfoList) {
		LockerResponse lockerResponse = setResponse(true);
		lockerResponse.setLockerInfoList(lockerInfoList);
		return lockerResponse;
	}
	
	public LockerResponse successResponse(LockerInfo lockerInfo) {
		LockerResponse lockerResponse = setResponse(true);
		lockerResponse.setLockerInfo(lockerInfo);
		return lockerResponse;
	}
	
	public LockerResponse deleteResponse(boolean isDeleted) {
		LockerResponse lockerResponse = setResponse(isDeleted);
		if(isDeleted) {
			lockerResponse.setMessage("delete success");
		} else {
			lockerResponse.setMessage("delete fail");
		}
		return lockerResponse;
	}
	
	private LockerResponse setResponse(boolean isSuccess) {
		CommonResponse commonResponse = super.successResponse();
		if(!isSuccess) {
			commonResponse = super.failResponse();
		}
		
		LockerResponse lockerResponse = new LockerResponse();
		lockerResponse.setCode(commonResponse.code);
		lockerResponse.setMessage(commonResponse.message);
		return lockerResponse;
	}
}
