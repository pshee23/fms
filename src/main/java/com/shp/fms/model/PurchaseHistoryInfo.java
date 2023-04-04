package com.shp.fms.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseHistoryInfo {
	private long purchaseId;
	private long productId;
	private long discountId;
	private long employeeId;
	private long memberId;
	private LocalDateTime purchaseDate;
	private LocalDate startDate;
	private LocalDate endDate;
}
