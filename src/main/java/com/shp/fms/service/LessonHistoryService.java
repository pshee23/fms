package com.shp.fms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shp.fms.controller.request.LessonHistoryRequestBody;
import com.shp.fms.model.LessonHistoryInfo;
import com.shp.fms.model.entity.Employee;
import com.shp.fms.model.entity.Lesson;
import com.shp.fms.model.entity.LessonHistory;
import com.shp.fms.model.entity.Member;
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
		return lessonHistoryMapper.mapToLessonHistoryInfoList(lessonHistoryList);
	}
}
