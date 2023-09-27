package com.shp.fms.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductInfo {
	private long productId;
	private String name;
	private long companyId;
	private long branchId;
	private int price;
	private LocalDate startDate;
	private LocalDate endDate;
	private String type;
}
