package com.shp.fms.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LockerInfo {
	private long lockerId;
	private long memberId;
	private long branchId;
	private LocalDate startDate;
	private LocalDate endDate;
	private String status;

	private static int EXPIRE_DAY = 7;
	
	@Builder
	public LockerInfo(long lockerId, long memberId, long branchId, LocalDate startDate, LocalDate endDate) {
		this.lockerId = lockerId;
		this.memberId = memberId;
		this.branchId = branchId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = makeStatus(); 
	}
	
	public enum Status {
		BEFORE_USE, IN_USE, ALERT, EXPIRED, EMPTY
	}
	
	public String makeStatus() { // TODO need test all case
		if(startDate == null || endDate == null) {
			return Status.EMPTY.name();
		}
		
		LocalDate now = LocalDate.now();
		
		if(now.isBefore(startDate)) {
			return Status.BEFORE_USE.name();
		} else if(now.equals(startDate) || now.isAfter(startDate) && now.isBefore(endDate.minusDays(EXPIRE_DAY))) {
			return Status.IN_USE.name();
		} else if(now.equals(endDate.minusDays(EXPIRE_DAY)) && now.isBefore(endDate)) {
			return Status.ALERT.name();
		} else {
			return Status.EXPIRED.name();
		}
	}
}
