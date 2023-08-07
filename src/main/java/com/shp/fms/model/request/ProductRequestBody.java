package com.shp.fms.model.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ProductRequestBody {
	private String name;
	private long companyId;
	private long branchId;
	private int price;
	private LocalDate startDate;
	private LocalDate endDate;
	private String type;
}
