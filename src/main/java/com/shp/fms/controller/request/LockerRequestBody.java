package com.shp.fms.controller.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LockerRequestBody {
	private long branchId;
	private long memberId;
	private LocalDate startDate;
	private LocalDate endDate;
}
