package com.shp.fms.repository.adapter;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Repository;

import com.shp.fms.model.entity.Member;
import com.shp.fms.repository.MemberRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class MemberPersistenceAdapter {

	private final MemberRepository memberRepository;
	
	public Member saveMember(Member Member) {
		return memberRepository.save(Member);
	}
	
	public void deleteMember(long MemberId) {
		memberRepository.deleteById(MemberId);
	}
	
	public List<Member> findAllMember() {
		return memberRepository.findAll();
	}
	
	public Member findMemberInfoById(Long MemberId) {
		Optional<Member> MemberOptional = memberRepository.findById(MemberId);
		return MemberOptional.orElseThrow(EntityNotFoundException::new);
	}
	
	public boolean isMemberExist(Long MemberId) {
		return memberRepository.existsById(MemberId);
	}
}
