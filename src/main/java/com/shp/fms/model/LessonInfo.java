package com.shp.fms.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonInfo {
	private long lessonId;
	private long memberId;
	private long employeeId;
	
	private int totalCount;
	private int currentCount;

	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
}
