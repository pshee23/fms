package com.shpfms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shpfms.model.MemberInfo;
import com.shpfms.model.entity.Member;
import com.shpfms.model.request.MemberRequestBody;
import com.shpfms.repository.adapter.MemberPersistenceAdapter;
import com.shpfms.repository.mapper.MemberMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberService {
	
	private final MemberPersistenceAdapter memberAdapter;
	
	private final MemberMapper memberMapper;

	public MemberInfo registerMember(MemberRequestBody registerInfo) {
		Member member = memberMapper.mapToMember(registerInfo);
		member = memberAdapter.saveMember(member);
		return memberMapper.mapToMemberInfo(member);
	}
	
	public MemberInfo modifyMember(long memberId, MemberRequestBody modifyInfo) {
		Member member = memberAdapter.findMemberInfoById(memberId);
		member = memberMapper.mapToMember(member, modifyInfo);
		member = memberAdapter.saveMember(member);
		return memberMapper.mapToMemberInfo(member);
	}
	
	public boolean deleteMember(long memberId) {
		memberAdapter.deleteMember(memberId);
		if(memberAdapter.isMemberExist(memberId)) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<MemberInfo> getAllMember() {
		List<Member> memberList = memberAdapter.findAllMember();
		return memberMapper.mapToMemberInfoList(memberList);
	}
	
	public MemberInfo getMemberById(long memberId) {
		Member member = memberAdapter.findMemberInfoById(memberId);
		return memberMapper.mapToMemberInfo(member);
	}
}
