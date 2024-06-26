package com.shp.fms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shp.fms.common.exception.NoDataReturnedException;
import com.shp.fms.common.exception.NoResultByIdException;
import com.shp.fms.common.type.ServiceType;
import com.shp.fms.model.MemberInfo;
import com.shp.fms.model.entity.Branch;
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
	
	private final MemberMapper memberMapper;

	public MemberInfo registerMember(MemberRequestBody registerInfo) {
		Branch branch = branchService.getBranchById(registerInfo.getBranchId());
		Member member = memberMapper.mapToMember(registerInfo, branch);
		member = memberRepository.save(member);
		return memberMapper.mapToMemberInfo(member);
	}
	
	public MemberInfo modifyMember(long memberId, MemberRequestBody modifyInfo) {
		Member member = getMemberById(memberId);
		Branch branch = branchService.getBranchById(modifyInfo.getBranchId());
		member = memberMapper.mapToMember(member, modifyInfo, branch);
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
	
	public Long getEmployeeIdByLoginId(String loginId) {
		Optional<Member> member = memberRepository.findByLoginId(loginId);
		if(member.isEmpty()) {
			return null;
		}
		return member.get().getId();
	}
	
	public List<MemberInfo> getAllMemberInfo() {
		List<Member> memberList = memberRepository.findAll();
		if(memberList.isEmpty()) {
			throw new NoDataReturnedException(ServiceType.MEMBER.getName());
		}
		return memberMapper.mapToMemberInfoList(memberList);
	}
	
	public Member getMemberById(long memberId) {
		Optional<Member> member = memberRepository.findById(memberId);
		if(member.isEmpty()) {
			throw new NoResultByIdException(memberId, ServiceType.MEMBER.getName());
		}
		return member.get();
	}
	
	public MemberInfo getMemberInfoById(long memberId) {
		return memberMapper.mapToMemberInfo(getMemberById(memberId));
	}
	
//	public List<MemberInfo> getMemberByEmployeeId(long employeeId) {
//		Optional<Member> memberList = memberRepository.findById(employeeId);
//		if(memberList.isEmpty()) {
//			throw new NoResultByIdException(employeeId, ServiceType.EMPLOYEE.getName());
//		}
//		return memberMapper.mapToMemberInfo(memberList.get());
//	}
	
	public List<MemberInfo> getMemberByBranchEmployeeId(long employeeId) {
		Optional<Member> employee = memberRepository.findById(employeeId);
		if(employee.isEmpty()) {
			throw new NoResultByIdException(employeeId, ServiceType.EMPLOYEE.getName());
		}
		
		List<Member> memberList = memberRepository.findByBranch_BranchId(employee.get().getBranch().getBranchId());
		if(memberList.isEmpty()) {
			throw new NoResultByIdException(employeeId, ServiceType.EMPLOYEE.getName());
		}
		return memberMapper.mapToMemberInfoList(memberList);
	}
	
	public List<MemberInfo> getMemberInfoListByBranchId(long branchId) {
		List<Member> memberList = memberRepository.findByBranch_BranchId(branchId);
		if(memberList.isEmpty()) {
			throw new NoResultByIdException(ServiceType.BRANCH.getName(), branchId, ServiceType.MEMBER.getName());
		}
		return memberMapper.mapToMemberInfoList(memberList);
	}
	
	public MemberInfo getMemberInfoByLoginId(String loginId) {
		Optional<Member> member = memberRepository.findByLoginId(loginId);
		if(member.isEmpty()) {
//			throw new UsernameNotFoundException("no user");
		}
		return memberMapper.mapToMemberInfo(member.get());
	}
	
	// TODO 세부 검색
	public boolean isMemberExist(String loginId) {
		boolean isExist = false;
		Optional<Member> member = memberRepository.findByLoginId(loginId);
		System.out.println(member);
		return isExist;
	}
}
