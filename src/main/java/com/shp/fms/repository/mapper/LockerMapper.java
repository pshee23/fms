package com.shp.fms.repository.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.shp.fms.controller.request.LockerRequestBody;
import com.shp.fms.model.LockerInfo;
import com.shp.fms.model.entity.Branch;
import com.shp.fms.model.entity.Locker;
import com.shp.fms.model.entity.Member;

@Component
public class LockerMapper {

	public LockerInfo mapToLockerInfo(Locker locker) {
		return LockerInfo.builder()
				.lockerId(locker.getLockerId())
				.branchId(locker.getBranch().getBranchId())
				.memberId(locker.getMember().getMemberId())
				.startDate(locker.getStartDate())
				.endDate(locker.getEndDate())
				.build();
	}
	
	public Locker mapToLocker(LockerRequestBody registerInfo, Branch branch, Member member) {
		Locker locker = new Locker();
		locker.setBranch(branch);
		locker.setMember(member);
		locker.setStartDate(registerInfo.getStartDate());
		locker.setEndDate(registerInfo.getEndDate());
		return locker;
	}
	
	public Locker mapToLocker(Locker originLocker, LockerRequestBody modifyInfo, Branch branch, Member member) {
		originLocker.setBranch(branch);
		originLocker.setMember(member);
		originLocker.setStartDate(modifyInfo.getStartDate());
		originLocker.setEndDate(modifyInfo.getEndDate());
		return originLocker;
	}
	
	public List<LockerInfo> mapToLockerInfoList(List<Locker> LockerList) {
		List<LockerInfo> LockerInfoList = new ArrayList<>();
		
		if(LockerList.isEmpty()) {
			return LockerInfoList;
		}
		
		for(Locker Locker : LockerList) {
			LockerInfo LockerInfo = mapToLockerInfo(Locker);
			LockerInfoList.add(LockerInfo);
		}
		
		return LockerInfoList;
	}
}
