package com.shpfms.repository.adapter;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Repository;

import com.shpfms.model.entity.Employee;
import com.shpfms.repository.EmployeeRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class EmployeePersistenceAdapter {

	private final EmployeeRepository employeeRepository;
	
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
	
	public void deleteEmployee(long employeeId) {
		employeeRepository.deleteById(employeeId);
	}
	
	public List<Employee> findAllEmployee() {
		return employeeRepository.findAll();
	}
	
	public Employee findEmployeeInfoById(Long employeeId) {
		Optional<Employee> EmployeeOptional = employeeRepository.findById(employeeId);
		return EmployeeOptional.orElseThrow(EntityNotFoundException::new);
	}
	
	public boolean isEmployeeExist(Long employeeId) {
		return employeeRepository.existsById(employeeId);
	}
}
