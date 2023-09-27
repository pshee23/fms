package com.shp.fms.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shp.fms.model.PayHistoryInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayHistoryResponse extends CommonResponse {
	private List<PayHistoryInfo> payHistoryInfoList;
	
	public PayHistoryResponse successResponse(List<PayHistoryInfo> payHistoryInfoList) {
		PayHistoryResponse payHistoryResponse = setResponse(true);
		payHistoryResponse.setPayHistoryInfoList(payHistoryInfoList);
		return payHistoryResponse;
	}
	
	private PayHistoryResponse setResponse(boolean isSuccess) {
		CommonResponse commonResponse = super.successResponse();
		if(!isSuccess) {
			commonResponse = super.failResponse();
		}
		
		PayHistoryResponse payHistoryResponse = new PayHistoryResponse();
		payHistoryResponse.setCode(commonResponse.code);
		payHistoryResponse.setMessage(commonResponse.message);
		return payHistoryResponse;
	}
}
