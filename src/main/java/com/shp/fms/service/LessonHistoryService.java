package com.shp.fms.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shp.fms.common.exception.NoResultByIdException;
import com.shp.fms.common.type.ServiceType;
import com.shp.fms.model.LessonHistoryInfo;
import com.shp.fms.model.entity.Employee;
import com.shp.fms.model.entity.Lesson;
import com.shp.fms.model.entity.LessonHistory;
import com.shp.fms.model.entity.Member;
import com.shp.fms.model.request.LessonHistoryRequestBody;
import com.shp.fms.repository.LessonHistoryRepository;
import com.shp.fms.repository.mapper.LessonHistoryMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonHistoryService {
	
	private final LessonHistoryRepository lessonHistoryRepository;
	
	private final EmployeeService employeeService;
	
	private final MemberService memberService;
	
	private final LessonService lessonService;
	
	private final LessonHistoryMapper lessonHistoryMapper;

	public void registerLessonHistory(LessonHistoryRequestBody registerInfo) {
		Employee employee = employeeService.getEmployeeById(registerInfo.getEmployeeId());
		Member member = memberService.getMemberById(registerInfo.getMemberId());
		Lesson lesson = lessonService.getLessonById(registerInfo.getLessonId());
		LessonHistory lessonHistory = lessonHistoryMapper.mapToLessonHistory(registerInfo, employee, member, lesson);
		
		lessonHistoryRepository.save(lessonHistory);

		// update Lesson
		lessonService.modifyLesson(registerInfo.getLessonId(), registerInfo.getLessonDateTime());
	}
		
	public List<LessonHistoryInfo> getAllLessonHistoryInfo(long memberId) {
		List<LessonHistory> lessonHistoryList = lessonHistoryRepository.findByMember_MemberId(memberId);
		if(lessonHistoryList.isEmpty()) {
			throw new NoResultByIdException(ServiceType.MEMBER.getName(), memberId, ServiceType.LESSON_HISTORY.getName());
		}
		return lessonHistoryMapper.mapToLessonHistoryInfoList(lessonHistoryList);
	}
	
	public List<LessonHistory> getLessonHistoryByEmployeeId(long employeeId) {
		List<LessonHistory> lessonHistoryList = lessonHistoryRepository.findByEmployee_EmployeeId(employeeId);
		if(lessonHistoryList.isEmpty()) {
			throw new NoResultByIdException(ServiceType.EMPLOYEE.getName(), employeeId, ServiceType.LESSON_HISTORY.getName());
		}
		return lessonHistoryList;
	}
	
	public List<LessonHistory> getAllByEmployeeIdAndDate(long employeeId, LocalDateTime startDate, LocalDateTime endDate) {
		List<LessonHistory> lessonHistoryList = lessonHistoryRepository.findAllByEmployee_EmployeeIdAndLessonDateTimeBetween(employeeId, startDate, endDate);
		if(lessonHistoryList.isEmpty()) {
			// TODO 다른 리턴 방식?
			throw new NoResultByIdException(ServiceType.EMPLOYEE.getName(), employeeId, ServiceType.LESSON_HISTORY.getName());
		}
		return lessonHistoryList;
	}
}
