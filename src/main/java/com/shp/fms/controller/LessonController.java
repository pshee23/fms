package com.shp.fms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shp.fms.model.LessonInfo;
import com.shp.fms.service.LessonService;

import lombok.AllArgsConstructor;

@RequestMapping("/lesson")
@RestController
@AllArgsConstructor
public class LessonController {
	
	private final LessonService lessonService;

	@GetMapping("/list")
	public ResponseEntity<List<LessonInfo>> getAllLessonList() {
		List<LessonInfo> lessonList = lessonService.getAllLesson();
		return ResponseEntity.ok(lessonList);
	}
}
