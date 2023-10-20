package com.shp.fms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shp.fms.common.exception.NoResultByIdException;
import com.shp.fms.common.type.ServiceType;
import com.shp.fms.model.LessonInfo;
import com.shp.fms.model.entity.Lesson;
import com.shp.fms.repository.LessonRepository;
import com.shp.fms.repository.mapper.LessonMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonService {
	
	private final LessonRepository lessonRepository;

	private final LessonMapper lessonMapper;

	public Lesson modifyLesson(long lessonId) {
		Lesson lesson = getLessonById(lessonId);
		lesson = lessonMapper.mapToLesson(lesson);
		return lessonRepository.save(lesson);
	}
	
	public List<LessonInfo> getAllLesson() {
		return lessonMapper.mapToLessonInfoList(lessonRepository.findAll());
	}
	
	public Lesson getLessonById(long lessonId) {
		Optional<Lesson> lesson = lessonRepository.findById(lessonId);
		if(lesson.isEmpty()) {
			throw new NoResultByIdException(lessonId, ServiceType.LESSON.getName());
		}
		return lesson.get();
	}
}
