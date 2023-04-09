package com.shp.fms.controller.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LessonHistoryRequestBody {
	private long memberId;
	private long lessonId;
	private long employeeId;
	private LocalDateTime lessonDateTime;
	private String status;
}
