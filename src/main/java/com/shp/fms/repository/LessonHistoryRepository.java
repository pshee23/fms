package com.shp.fms.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shp.fms.model.entity.LessonHistory;

public interface LessonHistoryRepository extends JpaRepository<LessonHistory, Long> {
	
//	List<LessonHistory> findTop10ByMember_EmployeeIdOrderByStartDateTimeAsc(long employeeId);
//	
//	List<LessonHistory> findAllByMember_EmployeeIdAndStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqualOrderByStartDateTimeAsc(long employeeId, LocalDateTime startDateTime, LocalDateTime endDateTime);
//	List<LessonHistory> findAllByMember_MemberIdAndStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqualOrderByStartDateTimeAsc(long memberId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
