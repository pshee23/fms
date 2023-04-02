package com.shp.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shp.fms.model.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
