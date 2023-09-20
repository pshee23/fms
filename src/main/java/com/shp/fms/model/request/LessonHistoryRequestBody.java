package com.shp.fms.model.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LessonHistoryRequestBody {
	private long memberId;
	private long lessonId;
	private long employeeId;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private String status;
	private LocalDateTime updateDateTime;
}
