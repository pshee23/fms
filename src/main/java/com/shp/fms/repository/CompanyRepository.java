package com.shp.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shp.fms.model.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
