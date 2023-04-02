package com.shp.fms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shp.fms.model.MemberInfo;
import com.shp.fms.model.entity.Branch;
import com.shp.fms.model.entity.Employee;
import com.shp.fms.model.entity.Member;
import com.shp.fms.model.request.MemberRequestBody;
import com.shp.fms.repository.MemberRepository;
import com.shp.fms.repository.mapper.MemberMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;

	private final BranchService branchService;
	
	private final EmployeeService employeeService;
	
	private final MemberMapper memberMapper;

	public MemberInfo registerMember(MemberRequestBody registerInfo) {
		Branch branch = branchService.getBranchById(registerInfo.getBranchId());
		Employee employee = employeeService.getEmployeeById(registerInfo.getEmployeeId());
		Member member = memberMapper.mapToMember(registerInfo, branch, employee);
		member = memberRepository.save(member);
		return memberMapper.mapToMemberInfo(member);
	}
	
	public MemberInfo modifyMember(long memberId, MemberRequestBody modifyInfo) {
		Member member = getMemberById(memberId);
		Branch branch = branchService.getBranchById(modifyInfo.getBranchId());
		Employee employee = employeeService.getEmployeeById(modifyInfo.getEmployeeId());
		member = memberMapper.mapToMember(member, modifyInfo, branch, employee);
		member = memberRepository.save(member);
		return memberMapper.mapToMemberInfo(member);
	}
	
	public boolean deleteMember(long memberId) {
		memberRepository.deleteById(memberId);
		if(memberRepository.existsById(memberId)) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<MemberInfo> getAllMemberInfo() {
		List<Member> memberList = memberRepository.findAll();
		return memberMapper.mapToMemberInfoList(memberList);
	}
	
	public Member getMemberById(long memberId) {
		Optional<Member> member = memberRepository.findById(memberId);
		if(member.isEmpty()) {
			// TODO throw exception
		}
		return member.get();
	}
	
	public MemberInfo getMemberInfoById(long memberId) {
		Optional<Member> member = memberRepository.findById(memberId);
		if(member.isEmpty()) {
			// TODO throw exception
		}
		return memberMapper.mapToMemberInfo(member.get());
	}
	
	public List<MemberInfo> getMemberInfoListByBranchId(long branchId) {
		List<Member> memberList = memberRepository.findByBranch_BranchId(branchId);
		return memberMapper.mapToMemberInfoList(memberList);
	}
}
