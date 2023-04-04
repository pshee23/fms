package com.shp.fms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shp.fms.controller.request.PurchaseHistoryRequestBody;
import com.shp.fms.controller.response.PurchaseHistoryResponse;
import com.shp.fms.model.PurchaseHistoryInfo;
import com.shp.fms.service.PurchaseHistoryService;

import lombok.AllArgsConstructor;

@RequestMapping("/product-history")
@RestController
@AllArgsConstructor
public class PurchaseHistoryController {
	
	private final PurchaseHistoryService purchaseHistoryService;
	
	@PostMapping
	public ResponseEntity<Object> registerProduct(@RequestBody PurchaseHistoryRequestBody requestBody) {
		purchaseHistoryService.registerPurchaseHistory(requestBody);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/list")
	public ResponseEntity<PurchaseHistoryResponse> getAllProduct() {
		List<PurchaseHistoryInfo> purchaseHistoryInfoList = purchaseHistoryService.getAllPurchaseHistoryInfo();
		return ResponseEntity.ok(new PurchaseHistoryResponse().successResponse(purchaseHistoryInfoList));
	}
	
}
