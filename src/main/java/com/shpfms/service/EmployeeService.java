package com.shpfms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shpfms.model.EmployeeInfo;
import com.shpfms.model.entity.Employee;
import com.shpfms.model.request.EmployeeRequestBody;
import com.shpfms.repository.adapter.EmployeePersistenceAdapter;
import com.shpfms.repository.mapper.EmployeeMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeService {
	
	private final EmployeePersistenceAdapter employeeAdapter;
	
	private final EmployeeMapper employeeMapper;

	public EmployeeInfo registerEmployee(EmployeeRequestBody registerInfo) {
		Employee employee = employeeMapper.mapToEmployee(registerInfo);
		employee = employeeAdapter.saveEmployee(employee);
		return employeeMapper.mapToEmployeeInfo(employee);
	}
	
	public EmployeeInfo modifyEmployee(long employeeId, EmployeeRequestBody modifyInfo) {
		Employee employee = employeeAdapter.findEmployeeInfoById(employeeId);
		employee = employeeMapper.mapToEmployee(employee, modifyInfo);
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
