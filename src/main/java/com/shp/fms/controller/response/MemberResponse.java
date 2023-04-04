package com.shp.fms.controller.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shp.fms.model.MemberInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberResponse extends CommonResponse {
	private List<MemberInfo> memberInfoList;
	private MemberInfo memberInfo;
	
	public MemberResponse successResponse(List<MemberInfo> memberInfoList) {
		MemberResponse memberResponse = setResponse(true);
		memberResponse.setMemberInfoList(memberInfoList);
		return memberResponse;
	}
	
	public MemberResponse successResponse(MemberInfo memberInfo) {
		MemberResponse memberResponse = setResponse(true);
		memberResponse.setMemberInfo(memberInfo);
		return memberResponse;
	}
	
	public MemberResponse deleteResponse(boolean isDeleted) {
		MemberResponse memberResponse = setResponse(isDeleted);
		if(isDeleted) {
			memberResponse.setMessage("delete success");
		} else {
			memberResponse.setMessage("delete fail");
		}
		return memberResponse;
	}
	
	private MemberResponse setResponse(boolean isSuccess) {
		CommonResponse commonResponse = super.successResponse();
		if(!isSuccess) {
			commonResponse = super.failResponse();
		}
		
		MemberResponse memberResponse = new MemberResponse();
		memberResponse.setCode(commonResponse.code);
		memberResponse.setMessage(commonResponse.message);
		return memberResponse;
	}
}
