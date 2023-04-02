package com.shp.fms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shp.fms.model.EmployeeInfo;
import com.shp.fms.model.entity.Branch;
import com.shp.fms.model.entity.Employee;
import com.shp.fms.model.request.EmployeeRequestBody;
import com.shp.fms.repository.adapter.EmployeePersistenceAdapter;
import com.shp.fms.repository.mapper.EmployeeMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeService {
	
	private final EmployeePersistenceAdapter employeeAdapter;
	
	private final BranchService branchService;
	
	private final EmployeeMapper employeeMapper;

	public EmployeeInfo registerEmployee(EmployeeRequestBody registerInfo) {
		Branch branch = branchService.getBranchById(registerInfo.getBranchId());
		Employee employee = employeeMapper.mapToEmployee(registerInfo, branch);
		employee = employeeAdapter.saveEmployee(employee);
		return employeeMapper.mapToEmployeeInfo(employee);
	}
	
	public EmployeeInfo modifyEmployee(long employeeId, EmployeeRequestBody modifyInfo) {
		Employee employee = employeeAdapter.findEmployeeInfoById(employeeId);
		Branch branch = branchService.getBranchById(modifyInfo.getBranchId());
		employee = employeeMapper.mapToEmployee(employee, modifyInfo, branch);
		employee = employeeAdapter.saveEmployee(employee);
		return employeeMapper.mapToEmployeeInfo(employee);
	}
	
	public boolean deleteEmployee(long employeeId) {
		employeeAdapter.deleteEmployee(employeeId);
		if(employeeAdapter.isEmployeeExist(employeeId)) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<EmployeeInfo> getAllEmployee() {
		List<Employee> employeeList = employeeAdapter.findAllEmployee();
		return employeeMapper.mapToEmployeeInfoList(employeeList);
	}
	
	public EmployeeInfo getEmployeeById(long employeeId) {
		Employee employee = employeeAdapter.findEmployeeInfoById(employeeId);
		return employeeMapper.mapToEmployeeInfo(employee);
	}
}
