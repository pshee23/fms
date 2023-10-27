package com.shp.fms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/list/employee/{employeeId}")
	public ResponseEntity<List<LessonInfo>> getAllLessonList(@PathVariable long employeeId) {
		List<LessonInfo> lessonList = lessonService.getLessonListByEmployeeId(employeeId);
		return ResponseEntity.ok(lessonList);
	}
}
