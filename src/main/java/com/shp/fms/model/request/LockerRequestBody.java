package com.shp.fms.model.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LockerRequestBody {
	private long branchId;
	private long memberId;
	private LocalDate startDate;
	private LocalDate endDate;
}
