package com.shp.fms.repository.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.shp.fms.controller.request.LessonHistoryRequestBody;
import com.shp.fms.model.LessonHistoryInfo;
import com.shp.fms.model.entity.Employee;
import com.shp.fms.model.entity.Lesson;
import com.shp.fms.model.entity.LessonHistory;
import com.shp.fms.model.entity.Member;

@Component
public class LessonHistoryMapper {

	public LessonHistoryInfo mapToLessonHistoryInfo(LessonHistory lessonListory) {
		return LessonHistoryInfo.builder()
				.lessonHistoryId(lessonListory.getLessonHistoryId())
				.employeeId(lessonListory.getEmployee().getEmployeeId())
				.memberId(lessonListory.getMember().getMemberId())
				.lessonId(lessonListory.getLesson().getLessonId())
				.lessonDate(lessonListory.getLessonDateTime())
				.status(lessonListory.getStatus())
				.build();
	}
	
	public LessonHistory mapToLessonHistory(LessonHistoryRequestBody registerInfo, Employee employee, Member member, Lesson lesson) {
		LessonHistory lessonHistory = new LessonHistory();
		lessonHistory.setEmployee(employee);
		lessonHistory.setMember(member);
		lessonHistory.setLesson(lesson);
		lessonHistory.setLessonDateTime(registerInfo.getLessonDateTime());
		lessonHistory.setStatus(registerInfo.getStatus());
		return lessonHistory;
	}

	public List<LessonHistoryInfo> mapToLessonHistoryInfoList(List<LessonHistory> lessonHistoryList) {
		List<LessonHistoryInfo> lessonHistoryInfoList = new ArrayList<>();
		
		if(lessonHistoryList.isEmpty()) {
			return lessonHistoryInfoList;
		}
		
		for(LessonHistory lessonHistory : lessonHistoryList) {
			LessonHistoryInfo lessonHistoryInfo = mapToLessonHistoryInfo(lessonHistory);
			lessonHistoryInfoList.add(lessonHistoryInfo);
		}
		
		return lessonHistoryInfoList;
	}
}
