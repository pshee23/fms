package com.shp.fms.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shp.fms.model.LessonHistoryInfo;
import com.shp.fms.model.request.LessonHistoryRequestBody;
import com.shp.fms.model.request.LessonHistorySearchRequestBody;
import com.shp.fms.service.LessonHistoryService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/lesson-history")
@RestController
@AllArgsConstructor
public class LessonHistoryController {
	
	private final LessonHistoryService lessonHistoryService;
	
	@PostMapping
	public ResponseEntity<Object> registerLessonHistory(@RequestBody LessonHistoryRequestBody requestBody) {
		log.info("register lesson-history. body={}", requestBody);
		lessonHistoryService.registerLessonHistory(requestBody);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/employee/{employeeId}/top")
	public ResponseEntity<List<LessonHistoryInfo>> getAllEmployeeLessonHistoryByRange(
			@PathVariable long employeeId) {
		log.info("get 10 lesson-history.");
		List<LessonHistoryInfo> lessonHistoryInfoList = lessonHistoryService.getTop10EmployeeLessonHistoryInfo(employeeId);
		log.info("result. response={}", lessonHistoryInfoList);
		return ResponseEntity.ok(lessonHistoryInfoList);
	}
	
	@PostMapping("/employee/{employeeId}")
	public ResponseEntity<List<LessonHistoryInfo>> getAllEmployeeLessonHistoryByRange(
			@PathVariable long employeeId, 
			@RequestBody LessonHistorySearchRequestBody requestBody) {
		log.info("get all lesson-history by range. startDate={}, endDate={}", requestBody);
		List<LessonHistoryInfo> lessonHistoryInfoList = lessonHistoryService.getAllEmployeeLessonHistoryInfoByRange(
				employeeId, requestBody.getOrderByTime(), requestBody.getOffset(), requestBody.getLimit(), requestBody.getStartDate(), requestBody.getEndDate());
		log.info("result. response={}", lessonHistoryInfoList);
		return ResponseEntity.ok(lessonHistoryInfoList);
	}
	
	@GetMapping("/employee/{employeeId}/datetime/{year}/{month}")
	public ResponseEntity<Map<LocalDate, List<String>>> getAllEmployeeLessonHistoryByDate(@PathVariable long employeeId, @PathVariable int year, @PathVariable int month) {
		log.info("get all lesson-history by Date. date={}-{}", year, month);
		Map<LocalDate, List<String>> lessonHistoryInfoList = lessonHistoryService.getAllEmployeeLessonHistoryMarkerByDate(employeeId, year, month);
		log.info("result. response={}", lessonHistoryInfoList);
		return ResponseEntity.ok(lessonHistoryInfoList);
	}

	@GetMapping("/employee/{employeeId}/datetime/{year}/{month}/{day}")
	public ResponseEntity<List<LessonHistoryInfo>> getAllEmployeeLessonHistoryByDateTime(@PathVariable long employeeId, @PathVariable int year, @PathVariable int month, @PathVariable int day) {
		LocalDate dateTimeConv = LocalDate.of(year, month, day);
		log.info("get all lesson-history by DateTime. dateTime={}", dateTimeConv);
		List<LessonHistoryInfo> lessonHistoryInfoList = lessonHistoryService.getAllEmployeeLessonHistoryInfoByDateTime(employeeId, dateTimeConv);
		log.info("result. response={}", lessonHistoryInfoList);
//		return ResponseEntity.ok(new LessonHistoryResponse().successResponse(lessonHistoryInfoList));
		return ResponseEntity.ok(lessonHistoryInfoList); //TODO flutter에서 list 객체 parse 방법 
	}
	
	@GetMapping("/member/{memberId}/datetime/{year}/{month}")
	public ResponseEntity<Map<LocalDate, List<String>>> getAllMemberLessonHistoryByDate(@PathVariable long memberId, @PathVariable int year, @PathVariable int month) {
		log.info("get all lesson-history by Date. date={}-{}", year, month);
		Map<LocalDate, List<String>> lessonHistoryInfoList = lessonHistoryService.getAllMemberLessonHistoryMarkerByDate(memberId, year, month);
		log.info("result. response={}", lessonHistoryInfoList);
//		return ResponseEntity.ok(new LessonHistoryResponse().successResponse(lessonHistoryInfoList));
		return ResponseEntity.ok(lessonHistoryInfoList); //TODO flutter에서 list 객체 parse 방법 
	}
	
	@GetMapping("/member/{memberId}/datetime/{year}/{month}/{day}")
	public ResponseEntity<List<LessonHistoryInfo>> getAllMemberLessonHistoryByDateTime(@PathVariable long memberId, @PathVariable int year, @PathVariable int month, @PathVariable int day) {
		LocalDate dateTimeConv = LocalDate.of(year, month, day);
		log.info("get all lesson-history by DateTime. dateTime={}", dateTimeConv);
		List<LessonHistoryInfo> lessonHistoryInfoList = lessonHistoryService.getAllMemberLessonHistoryInfoByDateTime(memberId, dateTimeConv);
		log.info("result. response={}", lessonHistoryInfoList);
//		return ResponseEntity.ok(new LessonHistoryResponse().successResponse(lessonHistoryInfoList));
		return ResponseEntity.ok(lessonHistoryInfoList); //TODO flutter에서 list 객체 parse 방법 
	}
	
	@PutMapping("/{lessonHistoryId}/status/{status}")
	public ResponseEntity<Object> changeLessonHistoryStatus(@PathVariable long lessonHistoryId, @PathVariable String status) {
		lessonHistoryService.changeLessonHistoryStatus(lessonHistoryId, status);
		return ResponseEntity.ok().build();
	}
}
