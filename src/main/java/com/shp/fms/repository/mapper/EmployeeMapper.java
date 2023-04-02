package com.shp.fms.repository.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.shp.fms.model.EmployeeInfo;
import com.shp.fms.model.entity.Branch;
import com.shp.fms.model.entity.Employee;
import com.shp.fms.model.request.EmployeeRequestBody;

@Component
public class EmployeeMapper {
	// TODO loginId, loginPw 는 개인 정보라 기본 CRUD에는 포함 안시키도록?

	public EmployeeInfo mapToEmployeeInfo(Employee employee) {
		return EmployeeInfo.builder()
				.branchId(employee.getBranch().getBranchId())
				.employeeId(employee.getEmployeeId())
				.name(employee.getName())
				.address(employee.getAddress())
				.phoneNumber(employee.getPhoneNumber())
				.loginId(employee.getLoginId())
				.role(employee.getRole())
				.status(employee.getStatus())
				.build();
	}
	
	public Employee mapToEmployee(EmployeeRequestBody registerInfo, Branch branch) {
		Employee employee = new Employee();
		employee.setBranch(branch);
		employee.setName(registerInfo.getName());
		employee.setAddress(registerInfo.getAddress());
		employee.setPhoneNumber(registerInfo.getPhoneNumber());
		employee.setRole(registerInfo.getRole());
		employee.setStatus(registerInfo.getStatus());
		return employee;
	}
	
	public Employee mapToEmployee(Employee originEmployee, EmployeeRequestBody modifyInfo, Branch branch) {
		originEmployee.setBranch(branch);
		originEmployee.setName(modifyInfo.getName());
		originEmployee.setAddress(modifyInfo.getAddress());
		originEmployee.setPhoneNumber(modifyInfo.getPhoneNumber());
		originEmployee.setRole(modifyInfo.getRole());
		originEmployee.setStatus(modifyInfo.getStatus());
		// TODO ID/PW 뺴고 업데이트하면 결과?
		return originEmployee;
	}
	
	public List<EmployeeInfo> mapToEmployeeInfoList(List<Employee> EmployeeList) {
		List<EmployeeInfo> EmployeeInfoList = new ArrayList<>();
		
		if(EmployeeList.isEmpty()) {
			return EmployeeInfoList;
		}
		
		for(Employee Employee : EmployeeList) {
			EmployeeInfo EmployeeInfo = mapToEmployeeInfo(Employee);
			EmployeeInfoList.add(EmployeeInfo);
		}
		
		return EmployeeInfoList;
	}
}
