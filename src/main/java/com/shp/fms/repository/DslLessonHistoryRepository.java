package com.shp.fms.repository;

import java.time.LocalDate;
import java.util.List;

import com.shp.fms.model.entity.LessonHistory;

public interface DslLessonHistoryRepository {
		
	List<LessonHistory> findFilteredOrderedSearchResults(long employeeId, int orderByTime, int offset, int limit, LocalDate startDate, LocalDate endDate);
	
}
