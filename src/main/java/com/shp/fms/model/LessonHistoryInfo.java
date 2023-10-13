package com.shp.fms.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonHistoryInfo {
	private long lessonHistoryId;
	
	private long employeeId;
	private String employeeName;
	
	private long memberId;
	private String memberName;
	
	private long lessonId;
	private String lessonName;
	
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	
	private String status;
}
