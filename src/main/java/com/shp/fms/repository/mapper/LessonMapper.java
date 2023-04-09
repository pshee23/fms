package com.shp.fms.repository.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.shp.fms.controller.request.LessonRequestBody;
import com.shp.fms.model.LessonInfo;
import com.shp.fms.model.entity.Lesson;
import com.shp.fms.model.entity.Member;

@Component
public class LessonMapper {

	public LessonInfo mapToLessonInfo(Lesson lesson) {
		return LessonInfo.builder()
				.lessonId(lesson.getLessonId())
				.memberId(lesson.getMember().getMemberId())
				.startDate(lesson.getStartDate())
				.totalCount(lesson.getTotalCount())
				.currentCount(lesson.getCurrentCount())
				.lastLessonTime(lesson.getLastLessonTime())
				.build();
	}
	
	public Lesson mapToLesson(LessonRequestBody registerInfo, Member member) {
		Lesson lesson = new Lesson();
		lesson.setMember(member);
		lesson.setTotalCount(registerInfo.getTotalCount());
		return lesson;
	}
	
	public Lesson mapToLesson(Lesson lesson, LocalDateTime lastLessonTime) {
		lesson.setLastLessonTime(lastLessonTime);
		lesson.setStartDate(lastLessonTime);
		lesson.setCurrentCount();
		return lesson;
	}
}
