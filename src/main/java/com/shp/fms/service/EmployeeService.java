package com.shp.fms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shp.fms.common.exception.NoDataReturnedException;
import com.shp.fms.common.exception.NoResultByIdException;
import com.shp.fms.common.type.ServiceType;
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
		Employee employee = getEmployeeById(employeeId);
		Branch branch = branchService.getBranchById(modifyInfo.getBranchId());
		Employee modifiedEmployee = employeeMapper.mapToEmployee(employee, modifyInfo, branch);
		modifiedEmployee = employeeRepository.save(modifiedEmployee);
		return employeeMapper.mapToEmployeeInfo(modifiedEmployee);
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
		if(employeeList.isEmpty()) {
			throw new NoDataReturnedException(ServiceType.EMPLOYEE.getName());
		}
		return employeeMapper.mapToEmployeeInfoList(employeeList);
	}
	
	public Employee getEmployeeById(long employeeId) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if(employee.isEmpty()) {
			throw new NoResultByIdException(employeeId, ServiceType.EMPLOYEE.getName());
		}
		return employee.get();
	}
	
	public EmployeeInfo getEmployeeInfoById(long employeeId) {
		return employeeMapper.mapToEmployeeInfo(getEmployeeById(employeeId));
	}

	public List<EmployeeInfo> getEmployeeInfoListByBranchId(long branchId) {
		List<Employee> employeeList = employeeRepository.findByBranch_BranchId(branchId);
		if(employeeList.isEmpty()) {
			throw new NoResultByIdException(ServiceType.BRANCH.getName(), branchId, ServiceType.EMPLOYEE.getName());
		}
		return employeeMapper.mapToEmployeeInfoList(employeeList);
	}
}
