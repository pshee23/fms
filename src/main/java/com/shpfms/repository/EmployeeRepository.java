package com.shpfms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shpfms.model.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
