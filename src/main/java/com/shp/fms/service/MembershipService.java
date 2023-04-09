package com.shp.fms.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shp.fms.model.entity.Member;
import com.shp.fms.model.entity.Membership;
import com.shp.fms.repository.MembershipRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MembershipService {
	
	private final MembershipRepository membershipRepository;

	private final MemberService memberService;

	public Membership registerMembership(long memberId, LocalDate startDate, LocalDate endDate) {
		Member member = memberService.getMemberById(memberId);
		Membership membership = Membership.builder()
				.member(member)
				.startDate(startDate)
				.endDate(endDate)
				.build();
		membership = membershipRepository.save(membership);
		return membership;
	}
	
	// TODO status 상태 정보 업데이트는 어떻게..
	// XXX 조회 시 계산 (MembershipInfo로 내보낼때..)
	
	public boolean deleteMembership(long memberId) {
		membershipRepository.deleteById(memberId);
		if(membershipRepository.existsById(memberId)) {
			return false;
		} else {
			return true;
		}
	}
	
	public Membership getMembershipById(long memberId) {
		Optional<Membership> membership = membershipRepository.findById(memberId);
		if(membership.isEmpty()) {
			// TODO throw exception
		}
		return membership.get();
	}
	
}
