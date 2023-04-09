package com.shp.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shp.fms.model.entity.Membership;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

}
