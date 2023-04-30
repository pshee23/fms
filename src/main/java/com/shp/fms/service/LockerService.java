package com.shp.fms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shp.fms.common.exception.NoDataReturnedException;
import com.shp.fms.common.exception.NoResultByIdException;
import com.shp.fms.common.type.ServiceType;
import com.shp.fms.controller.request.LockerRequestBody;
import com.shp.fms.model.LockerInfo;
import com.shp.fms.model.entity.Branch;
import com.shp.fms.model.entity.Locker;
import com.shp.fms.model.entity.Member;
import com.shp.fms.repository.LockerRepository;
import com.shp.fms.repository.mapper.LockerMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LockerService {
	
	private final LockerRepository lockerRepository;

	private final BranchService branchService;
	
	private final MemberService memberService;
	
	private final LockerMapper lockerMapper;

	public LockerInfo registerLocker(LockerRequestBody registerInfo) {
		Branch branch = branchService.getBranchById(registerInfo.getBranchId());
		Member member = memberService.getMemberById(registerInfo.getMemberId());
		Locker locker = lockerMapper.mapToLocker(registerInfo, branch, member);
		locker = lockerRepository.save(locker);
		return lockerMapper.mapToLockerInfo(locker);
	}
	
	public LockerInfo modifyLocker(long lockerId, LockerRequestBody modifyInfo) {
		Locker locker = getLockerById(lockerId);
		Branch branch = branchService.getBranchById(modifyInfo.getBranchId());
		Member member = memberService.getMemberById(modifyInfo.getMemberId());
		locker = lockerMapper.mapToLocker(locker, modifyInfo, branch, member);
		locker = lockerRepository.save(locker);
		return lockerMapper.mapToLockerInfo(locker);
	}
	
	public boolean deleteLocker(long lockerId) {
		lockerRepository.deleteById(lockerId);
		if(lockerRepository.existsById(lockerId)) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<LockerInfo> getAllLockerInfo() {
		List<Locker> lockerList = lockerRepository.findAll();
		if(lockerList.isEmpty()) {
			throw new NoDataReturnedException(ServiceType.LOCKER.getName());
		}
		return lockerMapper.mapToLockerInfoList(lockerList);
	}
	
	public Locker getLockerById(long lockerId) {
		Optional<Locker> locker = lockerRepository.findById(lockerId);
		if(locker.isEmpty()) {
			throw new NoResultByIdException(lockerId, ServiceType.LOCKER.getName());
		}
		return locker.get();
	}
	
	public LockerInfo getLockerInfoById(long lockerId) {
		return lockerMapper.mapToLockerInfo(getLockerById(lockerId));
	}
	
	public List<LockerInfo> getLockerInfoListByBranchId(long branchId) {
		List<Locker> lockerList = lockerRepository.findByBranch_BranchId(branchId);
		if(lockerList.isEmpty()) {
			throw new NoResultByIdException(ServiceType.BRANCH.getName(), branchId, ServiceType.LOCKER.getName());
		}
		return lockerMapper.mapToLockerInfoList(lockerList);
	}
}
