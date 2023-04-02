package com.shp.fms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shp.fms.model.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	List<Member> findByBranch_BranchId(long branchId);
}
