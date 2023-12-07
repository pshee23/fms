package com.shp.fms.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shp.fms.model.entity.LessonHistory;

public interface LessonHistoryRepository extends JpaRepository<LessonHistory, Long> {
	
	List<LessonHistory> findByMember_MemberId(long memberId);
	
	List<LessonHistory> findTop10ByEmployee_EmployeeIdOrderByStartDateTimeAsc(long employeeId);
	
//	List<LessonHistory> findByStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(LocalDateTime startDateTime, LocalDateTime endDateTime);
	
	List<LessonHistory> findByEmployee_EmployeeId(long employeeId);
	List<LessonHistory> findAllByEmployee_EmployeeIdAndStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqualOrderByStartDateTimeAsc(long employeeId, LocalDateTime startDateTime, LocalDateTime endDateTime);
	List<LessonHistory> findAllByMember_MemberIdAndStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqualOrderByStartDateTimeAsc(long memberId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
