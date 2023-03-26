package com.shpfms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shpfms.model.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
