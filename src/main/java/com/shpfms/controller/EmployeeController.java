package com.shpfms.controller;

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

import com.shpfms.model.EmployeeInfo;
import com.shpfms.model.request.EmployeeRequestBody;
import com.shpfms.service.EmployeeService;

import lombok.AllArgsConstructor;

@RequestMapping("/employee")
@RestController
@AllArgsConstructor
public class EmployeeController {

	private final EmployeeService employeeService;
	
	// Create New Employee
	@PostMapping
	public ResponseEntity<String> registerEmployee(@RequestBody EmployeeRequestBody requestBody) {
		EmployeeInfo employeeInfo = employeeService.registerEmployee(requestBody);
		return ResponseEntity.ok().build();
	}
	
	// Modify Exist Employee
	@PutMapping("/{employeeId}")
	public ResponseEntity<String> modifyEmployee(
			@PathVariable long employeeId,
			@RequestBody EmployeeRequestBody requestBody) {
		EmployeeInfo employeeInfo = employeeService.modifyEmployee(employeeId, requestBody);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{employeeId}")
	public ResponseEntity<String> deleteEmployee(@PathVariable long employeeId) {
		boolean deleteSuccess = employeeService.deleteEmployee(employeeId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/list")
	public ResponseEntity<String> getAllEmployee() {
		List<EmployeeInfo> employeeInfoList = employeeService.getAllEmployee();
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/list/{employeeId}")
	public ResponseEntity<String> getEmployeeById(@PathVariable long employeeId) {
		EmployeeInfo employeeInfo = employeeService.getEmployeeById(employeeId);
		return ResponseEntity.ok().build();
	}
	
}
