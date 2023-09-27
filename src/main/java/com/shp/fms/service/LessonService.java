package com.shp.fms.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shp.fms.common.exception.NoResultByIdException;
import com.shp.fms.common.type.ServiceType;
import com.shp.fms.model.LessonInfo;
import com.shp.fms.model.entity.Lesson;
import com.shp.fms.model.entity.Member;
import com.shp.fms.repository.LessonRepository;
import com.shp.fms.repository.mapper.LessonMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonService {
	
	private final LessonRepository lessonRepository;

	private final MemberService memberService;
	
	private final LessonMapper lessonMapper;

	public LessonInfo registerLesson(long memberId, int totalCount) {
		Member member = memberService.getMemberById(memberId);
		Lesson lesson = lessonMapper.mapToLesson(member, totalCount);
		lesson = lessonRepository.save(lesson);
		return lessonMapper.mapToLessonInfo(lesson);
	}
	
	public Lesson modifyLesson(long lessonId) {
		Lesson lesson = getLessonById(lessonId);
		lesson = lessonMapper.mapToLesson(lesson);
		return lessonRepository.save(lesson);
	}
	
	public boolean deleteLesson(long lessonId) {
		lessonRepository.deleteById(lessonId);
		if(lessonRepository.existsById(lessonId)) {
			return false;
		} else {
			return true;
		}
	}
	
	public Lesson getLessonById(long lessonId) {
		Optional<Lesson> lesson = lessonRepository.findById(lessonId);
		if(lesson.isEmpty()) {
			throw new NoResultByIdException(lessonId, ServiceType.LESSON.getName());
		}
		return lesson.get();
	}
	
	public LessonInfo getLessonInfoById(long lessonId) {
		return lessonMapper.mapToLessonInfo(getLessonById(lessonId));
	}
}
