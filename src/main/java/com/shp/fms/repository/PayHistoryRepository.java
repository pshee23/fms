package com.shp.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shp.fms.model.entity.PayHistory;

public interface PayHistoryRepository extends JpaRepository<PayHistory, Long> {
	
}
