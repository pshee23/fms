package com.shp.fms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shp.fms.model.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findByBranch_BranchId(long branchId);
	
	Optional<Employee> findByLoginId(String loginId);
	
}
