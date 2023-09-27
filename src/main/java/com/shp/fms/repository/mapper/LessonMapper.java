package com.shp.fms.repository.mapper;

import org.springframework.stereotype.Component;

import com.shp.fms.model.LessonInfo;
import com.shp.fms.model.entity.Lesson;
import com.shp.fms.model.entity.Member;

@Component
public class LessonMapper {

	public LessonInfo mapToLessonInfo(Lesson lesson) {
		return LessonInfo.builder()
				.lessonId(lesson.getLessonId())
				.memberId(lesson.getMember().getMemberId())
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
}
