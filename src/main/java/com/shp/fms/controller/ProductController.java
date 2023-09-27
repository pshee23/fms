package com.shp.fms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shp.fms.model.ProductInfo;
import com.shp.fms.model.request.ProductRequestBody;
import com.shp.fms.model.response.ProductResponse;
import com.shp.fms.service.ProductService;

import lombok.AllArgsConstructor;

@RequestMapping("/product")
@RestController
@AllArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
	@PostMapping
	public ResponseEntity<ProductResponse> registerProduct(@RequestBody ProductRequestBody requestBody) {
		ProductInfo productInfo = productService.registerProduct(requestBody);
		return ResponseEntity.ok(new ProductResponse().successResponse(productInfo));
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<ProductResponse> modifyProduct(
			@PathVariable long productId,
			@RequestBody ProductRequestBody requestBody) {
		ProductInfo productInfo = productService.modifyProduct(productId, requestBody);
		return ResponseEntity.ok(new ProductResponse().successResponse(productInfo));
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<ProductResponse> deleteProduct(@PathVariable long productId) {
		boolean isDeleted = productService.deleteProduct(productId);
		return ResponseEntity.ok(new ProductResponse().deleteResponse(isDeleted));	
	}
	
	// TODO list by 5
	@GetMapping("/list")
	public ResponseEntity<ProductResponse> getAllProduct() {
		List<ProductInfo> productInfoList = productService.getAllProductInfo();
		return ResponseEntity.ok(new ProductResponse().successResponse(productInfoList));
	}
	
	@GetMapping("/list/{productId}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable long productId) {
		ProductInfo productInfo = productService.getProductInfoById(productId);
		return ResponseEntity.ok(new ProductResponse().successResponse(productInfo));
	}
}
