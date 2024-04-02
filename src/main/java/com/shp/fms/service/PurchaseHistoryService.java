//package com.shp.fms.service;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.shp.fms.common.exception.NoDataReturnedException;
//import com.shp.fms.common.type.ServiceType;
//import com.shp.fms.model.PurchaseHistoryInfo;
//import com.shp.fms.model.entity.Member;
//import com.shp.fms.model.entity.Product;
//import com.shp.fms.model.entity.PurchaseHistory;
//import com.shp.fms.model.request.PurchaseHistoryRequestBody;
//import com.shp.fms.repository.PurchaseHistoryRepository;
//import com.shp.fms.repository.mapper.PurchaseHistoryMapper;
//
//import lombok.AllArgsConstructor;
//
//@Service
//@AllArgsConstructor
//public class PurchaseHistoryService {
//	
//	private final PurchaseHistoryRepository purchaseHistoryRepository;
//
//	private final ProductService productService;
//	
//	private final MemberService memberService;
//	
//	private final PurchaseHistoryMapper productHistoryMapper;
//
//	public PurchaseHistoryInfo registerPurchaseHistory(PurchaseHistoryRequestBody registerInfo) {
//		Product product = productService.getProductById(registerInfo.getProductId());
//		// TODO discount
//		Member employee = memberService.getMemberById(registerInfo.getEmployeeId());
//		Member member = memberService.getMemberById(registerInfo.getMemberId());
//		PurchaseHistory purchaseHistory = productHistoryMapper.mapToPurchaseHistory(registerInfo, product, employee, member);
//		purchaseHistory = purchaseHistoryRepository.save(purchaseHistory);
//		return productHistoryMapper.mapToPurchaseHistoryInfo(purchaseHistory);
//	}
//		
//	public List<PurchaseHistoryInfo> getAllPurchaseHistoryInfo() {
//		List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
//		if(purchaseHistoryList.isEmpty()) {
//			throw new NoDataReturnedException(ServiceType.PURCHASE_HISTORY.getName());
//		}
//		return productHistoryMapper.mapToPurchaseHistoryInfoList(purchaseHistoryList);
//	}
//}
