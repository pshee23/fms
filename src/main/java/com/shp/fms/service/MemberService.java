package com.shp.fms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shp.fms.model.MemberInfo;
import com.shp.fms.model.entity.Branch;
import com.shp.fms.model.entity.Employee;
import com.shp.fms.model.entity.Member;
import com.shp.fms.model.request.MemberRequestBody;
import com.shp.fms.repository.adapter.MemberPersistenceAdapter;
import com.shp.fms.repository.mapper.MemberMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberService {
	
	private final MemberPersistenceAdapter memberAdapter;

	private final BranchService branchService;
	
	private final EmployeeService employeeService;
	
	private final MemberMapper memberMapper;

	public MemberInfo registerMember(MemberRequestBody registerInfo) {
		Branch branch = branchService.getBranchById(registerInfo.getBranchId());
		Employee employee = employeeService.getEmployeeById(registerInfo.getEmployeeId());
		Member member = memberMapper.mapToMember(registerInfo, branch, employee);
		member = memberAdapter.saveMember(member);
		return memberMapper.mapToMemberInfo(member);
	}
	
	public MemberInfo modifyMember(long memberId, MemberRequestBody modifyInfo) {
		Member member = memberAdapter.findMemberInfoById(memberId);
		Branch branch = branchService.getBranchById(modifyInfo.getBranchId());
		Employee employee = employeeService.getEmployeeById(modifyInfo.getEmployeeId());
		member = memberMapper.mapToMember(member, modifyInfo, branch, employee);
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
