package com.shp.fms.repository.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.shp.fms.model.MemberInfo;
import com.shp.fms.model.entity.Branch;
import com.shp.fms.model.entity.Employee;
import com.shp.fms.model.entity.Member;
import com.shp.fms.model.request.MemberRequestBody;

@Component
public class MemberMapper {
	// TODO loginId, loginPw 는 개인 정보라 기본 CRUD에는 포함 안시키도록?

	public MemberInfo mapToMemberInfo(Member member) {
		return MemberInfo.builder()
				.branchId(member.getBranch().getBranchId())
				.employeeId(member.getEmployee().getEmployeeId())
				.memberId(member.getMemberId())
				.name(member.getName())
				.address(member.getAddress())
				.phoneNumber(member.getPhoneNumber())
				.loginId(member.getLoginId())
				.loginPw(member.getLoginPw())
				.role(member.getRole())
				.build();
	}
	
	public Member mapToMember(MemberRequestBody registerInfo, Branch branch, Employee employee) {
		Member member = new Member();
		member.setBranch(branch);
		member.setEmployee(employee);
		member.setName(registerInfo.getName());
		member.setAddress(registerInfo.getAddress());
		member.setPhoneNumber(registerInfo.getPhoneNumber());
		member.setLoginId(registerInfo.getLoginId());
		member.setLoginPw(registerInfo.getLoginPw());
		member.setRole(registerInfo.getRole());
		return member;
	}
	
	public Member mapToMember(Member originMember, MemberRequestBody modifyInfo, Branch branch, Employee employee) {
		originMember.setBranch(branch);
		originMember.setEmployee(employee);
		originMember.setName(modifyInfo.getName());
		originMember.setAddress(modifyInfo.getAddress());
		originMember.setPhoneNumber(modifyInfo.getPhoneNumber());
		// TODO ID/PW 뺴고 업데이트하면 결과?
		return originMember;
	}
	
	public List<MemberInfo> mapToMemberInfoList(List<Member> memberList) {
		List<MemberInfo> memberInfoList = new ArrayList<>();
		
		for(Member member : memberList) {
			MemberInfo memberInfo = mapToMemberInfo(member);
			memberInfoList.add(memberInfo);
		}
		
		return memberInfoList;
	}
}
