package com.shp.fms.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shp.fms.model.entity.LessonHistory;

public interface LessonHistoryRepository extends JpaRepository<LessonHistory, Long> {
	
	List<LessonHistory> findByMember_MemberId(long memberId);
	List<LessonHistory> findByEmployee_EmployeeId(long employeeId);
	List<LessonHistory> findAllByEmployee_EmployeeIdAndLessonDateTimeBetween(long employeeId, LocalDateTime startDate, LocalDateTime endDate);
}
