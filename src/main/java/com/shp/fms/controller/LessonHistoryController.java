package com.shp.fms.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shp.fms.model.LessonHistoryInfo;
import com.shp.fms.model.request.LessonHistoryRequestBody;
import com.shp.fms.model.response.LessonHistoryResponse;
import com.shp.fms.service.LessonHistoryService;

import lombok.AllArgsConstructor;

@RequestMapping("/lesson-history")
@RestController
@AllArgsConstructor
public class LessonHistoryController {
	
	private final LessonHistoryService lessonHistoryService;
	
	@PostMapping
	public ResponseEntity<Object> registerLessonHistory(@RequestBody LessonHistoryRequestBody requestBody) {
		lessonHistoryService.registerLessonHistory(requestBody);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/memberId/{memberId}")
	public ResponseEntity<LessonHistoryResponse> getLessonHistoryByMemberId(@PathVariable long memberId) {
		List<LessonHistoryInfo> lessonHistoryInfoList = lessonHistoryService.getAllLessonHistoryInfoByMemberId(memberId);
		return ResponseEntity.ok(new LessonHistoryResponse().successResponse(lessonHistoryInfoList));
	}
	
	@GetMapping("/datetime/{year}/{month}/{day}")
	public ResponseEntity<List<LessonHistoryInfo>> getAllLessonHistoryByDateTime(@PathVariable int year, @PathVariable int month, @PathVariable int day) {
		LocalDate dateTimeConv = LocalDate.of(year, month, day);
//		List<LessonHistoryInfo> lessonHistoryInfoList = lessonHistoryService.getAllLessonHistoryInfoByDateTime(dateTimeConv);
		return ResponseEntity.ok(lessonHistoryService.getAllLessonHistoryInfoByDateTime(dateTimeConv));
	}
}
