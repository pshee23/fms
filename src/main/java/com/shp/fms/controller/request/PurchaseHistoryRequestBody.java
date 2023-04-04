package com.shp.fms.controller.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PurchaseHistoryRequestBody {
	private long productId;
	private long discountId;
	private long employeeId;
	private long memberId;
	private LocalDateTime purchaseDate;
	private LocalDate startDate;
	private LocalDate endDate;
}
