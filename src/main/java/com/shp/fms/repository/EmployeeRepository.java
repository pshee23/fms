package com.shp.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shp.fms.model.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
