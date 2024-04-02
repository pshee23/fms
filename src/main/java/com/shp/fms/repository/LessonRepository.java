package com.shp.fms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shp.fms.model.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
	
	List<Lesson> findByEmployee_Id(long employeeId);
}
