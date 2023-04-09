package com.shp.fms.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonInfo {
	private long lessonId;
	private long memberId;
	private LocalDate startDate;
	private int totalCount;
	private int currentCount;
	private LocalDateTime lastLessonTime;
}
