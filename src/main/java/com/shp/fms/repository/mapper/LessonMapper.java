package com.shp.fms.repository.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.shp.fms.model.LessonInfo;
import com.shp.fms.model.entity.Lesson;
import com.shp.fms.model.entity.Member;

@Component
public class LessonMapper {

	public LessonInfo mapToLessonInfo(Lesson lesson) {
		return LessonInfo.builder()
				.lessonId(lesson.getLessonId())
				.lessonName(lesson.getName())
				.memberId(lesson.getMember().getMemberId())
				.memberName(lesson.getMember().getName())
				.employeeId(lesson.getEmployee().getEmployeeId())
				.employeeName(lesson.getEmployee().getName())
				.startDateTime(lesson.getStartDateTime())
				.totalCount(lesson.getTotalCount())
				.currentCount(lesson.getCurrentCount())
				.endDateTime(lesson.getEndDateTime())
				.build();
	}
	
	public Lesson mapToLesson(Member member, int totalCount) {
		Lesson lesson = new Lesson();
		lesson.setMember(member);
		lesson.setTotalCount(totalCount);
		return lesson;
	}
	
	public Lesson mapToLesson(Lesson lesson) {
		lesson.updateDateTime();
		lesson.setCurrentCount();
		return lesson;
	}
	
	public List<LessonInfo> mapToLessonInfoList(List<Lesson> lessonList) {
		List<LessonInfo> lessonInfoList = new ArrayList<>();
		
		for(Lesson lesson : lessonList) {
			LessonInfo lessonInfo = mapToLessonInfo(lesson);
			lessonInfoList.add(lessonInfo);
		}
		return lessonInfoList;
	}
}
