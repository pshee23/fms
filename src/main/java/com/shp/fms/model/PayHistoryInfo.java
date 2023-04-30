package com.shp.fms.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayHistoryInfo {
	private long payHistoryId;
	private long employeeId;
	private int salary;
	private LocalDateTime payDateTime;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
}
