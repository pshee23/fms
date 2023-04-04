package com.shp.fms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shp.fms.model.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByBranch_BranchId(long branchId);
	
}
