package com.shp.fms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shp.fms.model.entity.Company;
import com.shp.fms.model.request.CompanyRequestBody;
import com.shp.fms.repository.CompanyRepository;

import lombok.AllArgsConstructor;

@RequestMapping("/company")
@RestController
@AllArgsConstructor
public class CompanyController {
	
	private final CompanyRepository companyRepository;
	
	// TODO do it..
	@PostMapping
	public ResponseEntity<Object> registerCompany(@RequestBody CompanyRequestBody requestBody) {
		Company company = new Company();
		company.setName(requestBody.getName());
		company.setAddress(requestBody.getAddress());
		company.setPhoneNumber(requestBody.getPhoneNumber());
		companyRepository.save(company);
		return ResponseEntity.ok().build();
	}
}
