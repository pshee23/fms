package com.shp.fms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shp.fms.controller.request.EmployeeRequestBody;
import com.shp.fms.model.EmployeeInfo;
import com.shp.fms.model.entity.Branch;
import com.shp.fms.model.entity.Employee;
import com.shp.fms.repository.EmployeeRepository;
import com.shp.fms.repository.mapper.EmployeeMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeService {
	
	private final EmployeeRepository employeeRepository;
	
	private final BranchService branchService;
	
	private final EmployeeMapper employeeMapper;

	public EmployeeInfo registerEmployee(EmployeeRequestBody registerInfo) {
		Branch branch = branchService.getBranchById(registerInfo.getBranchId());
		Employee employee = employeeMapper.mapToEmployee(registerInfo, branch);
		employee = employeeRepository.save(employee);
		return employeeMapper.mapToEmployeeInfo(employee);
	}
	
	public EmployeeInfo modifyEmployee(long employeeId, EmployeeRequestBody modifyInfo) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
		if(optionalEmployee.isEmpty()) {
			// TODO throw exception
		}
		Branch branch = branchService.getBranchById(modifyInfo.getBranchId());
		Employee employee = employeeMapper.mapToEmployee(optionalEmployee.get(), modifyInfo, branch);
		employee = employeeRepository.save(employee);
		return employeeMapper.mapToEmployeeInfo(employee);
	}
	
	public boolean deleteEmployee(long employeeId) {
		employeeRepository.deleteById(employeeId);
		if(employeeRepository.existsById(employeeId)) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<EmployeeInfo> getAllEmployeeInfo() {
		List<Employee> employeeList = employeeRepository.findAll();
		return employeeMapper.mapToEmployeeInfoList(employeeList);
	}
	
	public Employee getEmployeeById(long employeeId) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if(employee.isEmpty()) {
			// TODO throw exception
		}
		return employee.get();
	}
	
	public EmployeeInfo getEmployeeInfoById(long employeeId) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if(employee.isEmpty()) {
			// TODO throw exception
		}
		return employeeMapper.mapToEmployeeInfo(employee.get());
	}

	public List<EmployeeInfo> getEmployeeInfoListByBranchId(long branchId) {
		List<Employee> employeeList = employeeRepository.findByBranch_BranchId(branchId);
		return employeeMapper.mapToEmployeeInfoList(employeeList);
	}
}
