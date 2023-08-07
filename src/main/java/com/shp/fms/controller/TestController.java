package com.shp.fms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RequestMapping("/test")
@RestController
@AllArgsConstructor
public class TestController {
	
	@GetMapping
	public ResponseEntity<String> getTest() {
		return ResponseEntity.ok("Hello");
	}
}
