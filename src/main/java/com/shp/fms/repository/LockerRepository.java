package com.shp.fms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shp.fms.model.entity.Locker;

public interface LockerRepository extends JpaRepository<Locker, Long> {

	List<Locker> findByBranch_BranchId(long branchId);
	
}
