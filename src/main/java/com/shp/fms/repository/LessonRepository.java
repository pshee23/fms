package com.shp.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shp.fms.model.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
