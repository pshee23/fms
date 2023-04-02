package com.shp.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shp.fms.model.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {

}
