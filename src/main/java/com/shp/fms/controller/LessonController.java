package com.shp.fms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shp.fms.controller.request.LessonRequestBody;
import com.shp.fms.controller.response.LessonResponse;
import com.shp.fms.model.LessonInfo;
import com.shp.fms.service.LessonService;

import lombok.AllArgsConstructor;

@RequestMapping("/lesson")
@RestController
@AllArgsConstructor
public class LessonController {
	
	private final LessonService lessonService;
	
	@PostMapping
	public ResponseEntity<LessonResponse> registerLesson(@RequestBody LessonRequestBody requestBody) {
		LessonInfo lessonInfo = lessonService.registerLesson(requestBody);
		return ResponseEntity.ok(new LessonResponse().successResponse(lessonInfo));
	}
	
	// XXX 수정은 Lesson History로만 이루어짐
	
	// XXX 삭제도.. 위와 같을 듯?
	
	@GetMapping
	public ResponseEntity<LessonResponse> getLessonById(@PathVariable long lessonId) {
		LessonInfo lessonInfo = lessonService.getLessonInfoById(lessonId);
		return ResponseEntity.ok(new LessonResponse().successResponse(lessonInfo));
	}
}
