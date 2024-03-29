package com.shp.fms.model.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PayHistoryRequestBody {
	private long employeeId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private LocalDateTime payDateTime;
}
