package com.shp.fms.repository.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.shp.fms.model.PurchaseHistoryInfo;
import com.shp.fms.model.entity.Employee;
import com.shp.fms.model.entity.Member;
import com.shp.fms.model.entity.Product;
import com.shp.fms.model.entity.PurchaseHistory;
import com.shp.fms.model.request.PurchaseHistoryRequestBody;

@Component
public class PurchaseHistoryMapper {

	public PurchaseHistoryInfo mapToPurchaseHistoryInfo(PurchaseHistory purchaseHistory) {
		return PurchaseHistoryInfo.builder()
				.purchaseId(purchaseHistory.getPurchaseId())
				.productId(purchaseHistory.getProduct().getProductId())
				// TODO discount
				.employeeId(purchaseHistory.getEmployee().getEmployeeId())
				.memberId(purchaseHistory.getMember().getMemberId())
				.purchaseDate(purchaseHistory.getPurchaseDate())
				.startDate(purchaseHistory.getStartDate())
				.endDate(purchaseHistory.getEndDate())
				.build();
	}
	
	public PurchaseHistory mapToPurchaseHistory(PurchaseHistoryRequestBody registerInfo, Product product, Employee employee, Member member) {
		PurchaseHistory purchaseHistory = new PurchaseHistory();
		purchaseHistory.setProduct(product);
		// TODO discount
		purchaseHistory.setEmployee(employee);
		purchaseHistory.setMember(member);
		purchaseHistory.setPurchaseDate(registerInfo.getPurchaseDate());
		purchaseHistory.setStartDate(registerInfo.getStartDate());
		purchaseHistory.setEndDate(registerInfo.getEndDate());
		return purchaseHistory;
	}
	
	public List<PurchaseHistoryInfo> mapToPurchaseHistoryInfoList(List<PurchaseHistory> purchaseHistoryList) {
		List<PurchaseHistoryInfo> purchaseHistoryInfoList = new ArrayList<>();
		
		for(PurchaseHistory purchaseHistory : purchaseHistoryList) {
			PurchaseHistoryInfo purchaseHistoryInfo = mapToPurchaseHistoryInfo(purchaseHistory);
			purchaseHistoryInfoList.add(purchaseHistoryInfo);
		}
		
		return purchaseHistoryInfoList;
	}
}
