package com.shp.fms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shp.fms.model.PayHistoryInfo;
import com.shp.fms.model.request.PayHistoryRequestBody;
import com.shp.fms.model.response.PayHistoryResponse;
import com.shp.fms.service.PayHistoryService;

import lombok.AllArgsConstructor;

@RequestMapping("/pay-history")
@RestController
@AllArgsConstructor
public class PayHistoryController {
	
	private final PayHistoryService payHistoryService;
	
	@PostMapping
	public ResponseEntity<Object> registerPayHistory(@RequestBody PayHistoryRequestBody requestBody) {
		payHistoryService.registerPayHistory(requestBody);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/list")
	public ResponseEntity<PayHistoryResponse> getAllPayHistory() {
		List<PayHistoryInfo> payHistoryInfoList = payHistoryService.getAllPayHistoryInfo();
		return ResponseEntity.ok(new PayHistoryResponse().successResponse(payHistoryInfoList));
	}
	
}
