package com.shp.fms.controller.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shp.fms.model.PurchaseHistoryInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseHistoryResponse extends CommonResponse {
	private List<PurchaseHistoryInfo> purchaseHistoryInfoList;
	
	public PurchaseHistoryResponse successResponse(List<PurchaseHistoryInfo> purchaseHistoryInfoList) {
		PurchaseHistoryResponse productResponse = setResponse(true);
		productResponse.setPurchaseHistoryInfoList(purchaseHistoryInfoList);
		return productResponse;
	}
	
	private PurchaseHistoryResponse setResponse(boolean isSuccess) {
		CommonResponse commonResponse = super.successResponse();
		if(!isSuccess) {
			commonResponse = super.failResponse();
		}
		
		PurchaseHistoryResponse productResponse = new PurchaseHistoryResponse();
		productResponse.setCode(commonResponse.code);
		productResponse.setMessage(commonResponse.message);
		return productResponse;
	}
}
