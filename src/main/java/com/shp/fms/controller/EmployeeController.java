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

import com.shp.fms.controller.request.EmployeeRequestBody;
import com.shp.fms.controller.response.EmployeeResponse;
import com.shp.fms.model.EmployeeInfo;
import com.shp.fms.service.EmployeeService;

import lombok.AllArgsConstructor;

@RequestMapping("/employee")
@RestController
@AllArgsConstructor
public class EmployeeController {

	private final EmployeeService employeeService;
	
	// Create New Employee
	@PostMapping
	public ResponseEntity<EmployeeResponse> registerEmployee(@RequestBody EmployeeRequestBody requestBody) {
		EmployeeInfo employeeInfo = employeeService.registerEmployee(requestBody);
		return ResponseEntity.ok(new EmployeeResponse().successResponse(employeeInfo));
	}
	
	// Modify Exist Employee
	@PutMapping("/{employeeId}")
	public ResponseEntity<EmployeeResponse> modifyEmployee(
			@PathVariable long employeeId,
			@RequestBody EmployeeRequestBody requestBody) {
		EmployeeInfo employeeInfo = employeeService.modifyEmployee(employeeId, requestBody);
		return ResponseEntity.ok(new EmployeeResponse().successResponse(employeeInfo));
	}
	
	@DeleteMapping("/{employeeId}")
	public ResponseEntity<EmployeeResponse> deleteEmployee(@PathVariable long employeeId) {
		boolean isDeleted = employeeService.deleteEmployee(employeeId);
		return ResponseEntity.ok(new EmployeeResponse().deleteResponse(isDeleted));
	}
	
	@GetMapping("/list")
	public ResponseEntity<EmployeeResponse> getAllEmployee() {
		List<EmployeeInfo> employeeInfoList = employeeService.getAllEmployeeInfo();
		return ResponseEntity.ok(new EmployeeResponse().successResponse(employeeInfoList));
	}
	
	@GetMapping("/list/{employeeId}")
	public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable long employeeId) {
		EmployeeInfo employeeInfo = employeeService.getEmployeeInfoById(employeeId);
		return ResponseEntity.ok(new EmployeeResponse().successResponse(employeeInfo));
	}
	
	@GetMapping("/list/branch/{branchId}")
	public ResponseEntity<EmployeeResponse> getEmployeeListByBranchId(@PathVariable long branchId) {
		List<EmployeeInfo> employeeInfo = employeeService.getEmployeeInfoListByBranchId(branchId);
		return ResponseEntity.ok(new EmployeeResponse().successResponse(employeeInfo));
	}
}
