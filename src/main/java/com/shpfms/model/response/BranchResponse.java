package com.shpfms.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shpfms.model.BranchInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BranchResponse extends CommonResponse {
	private List<BranchInfo> branchInfoList;
	private BranchInfo branchInfo;
	
	public BranchResponse successResponse(List<BranchInfo> branchInfoList) {
		BranchResponse branchResponse = setResponse(true);
		branchResponse.setBranchInfoList(branchInfoList);
		return branchResponse;
	}
	
	public BranchResponse successResponse(BranchInfo branchInfo) {
		BranchResponse branchResponse = setResponse(true);
		branchResponse.setBranchInfo(branchInfo);
		return branchResponse;
	}
	
	public BranchResponse deleteResponse(boolean isDeleted) {
		BranchResponse branchResponse = setResponse(isDeleted);
		if(isDeleted) {
			branchResponse.setMessage("delete success");
		} else {
			branchResponse.setMessage("delete fail");
		}
		return branchResponse;
	}
	
	private BranchResponse setResponse(boolean isSuccess) {
		CommonResponse commonResponse = super.successResponse();
		if(!isSuccess) {
			commonResponse = super.failResponse();
		}
		
		BranchResponse branchResponse = new BranchResponse();
		branchResponse.setCode(commonResponse.code);
		branchResponse.setMessage(commonResponse.message);
		return branchResponse;
	}
}
