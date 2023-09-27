package com.shp.fms.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shp.fms.model.ProductInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse extends CommonResponse {
	private List<ProductInfo> productInfoList;
	private ProductInfo productInfo;
	
	public ProductResponse successResponse(List<ProductInfo> productInfoList) {
		ProductResponse productResponse = setResponse(true);
		productResponse.setProductInfoList(productInfoList);
		return productResponse;
	}
	
	public ProductResponse successResponse(ProductInfo productInfo) {
		ProductResponse productResponse = setResponse(true);
		productResponse.setProductInfo(productInfo);
		return productResponse;
	}
	
	public ProductResponse deleteResponse(boolean isDeleted) {
		ProductResponse productResponse = setResponse(isDeleted);
		if(isDeleted) {
			productResponse.setMessage("delete success");
		} else {
			productResponse.setMessage("delete fail");
		}
		return productResponse;
	}
	
	private ProductResponse setResponse(boolean isSuccess) {
		CommonResponse commonResponse = super.successResponse();
		if(!isSuccess) {
			commonResponse = super.failResponse();
		}
		
		ProductResponse productResponse = new ProductResponse();
		productResponse.setCode(commonResponse.code);
		productResponse.setMessage(commonResponse.message);
		return productResponse;
	}
}
