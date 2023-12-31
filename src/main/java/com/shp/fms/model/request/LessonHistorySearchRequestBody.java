package com.shp.fms.model.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LessonHistorySearchRequestBody {
	private long employeeId;
	private int orderByTime;
	private int offset;
	private int limit;
	private LocalDate startDate;
	private LocalDate endDate;
}
