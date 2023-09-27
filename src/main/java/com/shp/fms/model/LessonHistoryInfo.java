package com.shp.fms.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonHistoryInfo {
	private long lessonHistoryId;
	private long employeeId;
	private long memberId;
	private long lessonId;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private String status;
}
