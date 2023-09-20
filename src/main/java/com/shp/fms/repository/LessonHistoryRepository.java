package com.shp.fms.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shp.fms.model.entity.LessonHistory;

public interface LessonHistoryRepository extends JpaRepository<LessonHistory, Long> {
	
	List<LessonHistory> findByMember_MemberId(long memberId);
	
	List<LessonHistory> findAllByStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(LocalDateTime startDateTime, LocalDateTime endDateTime);
	
	List<LessonHistory> findByEmployee_EmployeeId(long employeeId);
	List<LessonHistory> findAllByEmployee_EmployeeIdAndStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(long employeeId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
