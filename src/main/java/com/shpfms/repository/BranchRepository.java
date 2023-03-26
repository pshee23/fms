package com.shpfms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shpfms.model.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {

}
