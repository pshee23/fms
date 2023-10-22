package com.shp.fms.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonInfo {
	private long lessonId;
	private String lessonName;
	
	private long memberId;
	private String memberName;
	
	private long employeeId;
	private String employeeName;
	
	private int totalCount;
	private int currentCount;

	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
}
