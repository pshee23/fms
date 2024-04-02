package com.shp.fms.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shp.fms.common.exception.NoResultByIdException;
import com.shp.fms.common.type.ServiceType;
import com.shp.fms.model.LessonHistoryInfo;
import com.shp.fms.model.LessonInfo;
import com.shp.fms.model.MemberInfo;
import com.shp.fms.model.entity.Lesson;
import com.shp.fms.model.entity.LessonHistory;
import com.shp.fms.repository.LessonRepository;
import com.shp.fms.repository.mapper.LessonHistoryMapper;
import com.shp.fms.repository.mapper.LessonMapper;
import com.shp.fms.repository.mapper.MemberMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class LessonService {
	
	private final LessonRepository lessonRepository;

	private final LessonMapper lessonMapper;
	
	private final MemberMapper memberMapper;
	
	private final LessonHistoryMapper lessonHistoryMapper;

	public Lesson modifyLesson(long lessonId) {
		Lesson lesson = getLessonById(lessonId);
		lesson = lessonMapper.mapToLesson(lesson);
		return lessonRepository.save(lesson);
	}
	
	public List<LessonInfo> getLessonInfoListByEmployeeId(long employeeId) {
		return lessonMapper.mapToLessonInfoList(lessonRepository.findByEmployee_Id(employeeId));
	}
	
	public List<Lesson> getLessonListByEmployeeId(long employeeId) {
		return lessonRepository.findByEmployee_Id(employeeId);
	}
	
	public Lesson getLessonById(long lessonId) {
		Optional<Lesson> lesson = lessonRepository.findById(lessonId);
		if(lesson.isEmpty()) {
			throw new NoResultByIdException(lessonId, ServiceType.LESSON.getName());
		}
		return lesson.get();
	}
	
	public List<MemberInfo> getMemberListByEmployeeId(long employeeId) {
		List<Lesson> lessonList = lessonRepository.findByEmployee_Id(employeeId);
		List<MemberInfo> memberInfoList = new ArrayList<>();
		for(Lesson lesson : lessonList) {
			MemberInfo memberInfo = memberMapper.mapToMemberInfo(lesson.getMember());
			memberInfoList.add(memberInfo);
		}
		return memberInfoList;
	}
	
	public List<LessonHistoryInfo> getTop10EmployeeLessonHistoryInfo(long employeeId) {
		log.info("get 10 lesson history.");
		List<Lesson> lessonList = lessonRepository.findByEmployee_Id(employeeId);
		List<LessonHistory> historyListResult = new ArrayList<>();
		for(Lesson lesson : lessonList) {
			List<LessonHistory> historyList = lesson.getHistoryList()
					.stream()
					.sorted(Comparator.comparing(LessonHistory::getStartDateTime))
					.limit(10)
					.collect(Collectors.toList());
			historyListResult.addAll(historyList);
		}
		
		List<LessonHistory> lessonHistoryList = historyListResult.stream().limit(10).collect(Collectors.toList());
		log.info("getTop10EmployeeLessonHistoryInfo. resultMap={}", lessonHistoryList);
		return lessonHistoryMapper.mapToLessonHistoryInfoList(lessonHistoryList);
	}
	
	private List<LessonHistory> findRangedHistoryByEmployeeId(long employeeId, LocalDateTime startTime, LocalDateTime endTime) {
		List<Lesson> lessonList = lessonRepository.findByEmployee_Id(employeeId);
		List<LessonHistory> lessonHistoryList = new ArrayList<>();
		for(Lesson lesson : lessonList) {
			List<LessonHistory> historyList = lesson.getHistoryList()
					.stream()
					.filter(v -> v.getStartDateTime().isAfter(startTime) && v.getEndDateTime().isBefore(endTime))
					.sorted(Comparator.comparing(LessonHistory::getStartDateTime))
					.collect(Collectors.toList());
			lessonHistoryList.addAll(historyList);
		}
		log.info("findRangedHistoryByEmployeeId. resultMap={}", lessonHistoryList);
		return lessonHistoryList;
	}
	
	public Map<LocalDate, List<String>> getAllMemberLessonHistoryMarkerByDate(long userId, int year, int month) {
		LocalDateTime startTime = LocalDateTime.of(year, month, 1, 0, 0);
		LocalDateTime endTime = LocalDateTime.of(year, month, 1, 0, 0).plusMonths(1).minusSeconds(1);
		log.info("get all lesson history. startTime={} ~ endTime={}", startTime, endTime);
		
		List<LessonHistory> historyListResult = findRangedHistoryByEmployeeId(userId, startTime, endTime);
		
		Map<LocalDate, List<String>> resultMap = new HashMap<>();
		for(LessonHistory history : historyListResult) {
			LocalDate localDate = history.getStartDateTime().toLocalDate();
			List<String> stringList = resultMap.getOrDefault(localDate, new ArrayList<>());
			stringList.add(history.getId().toString());
			resultMap.put(localDate, stringList);
		}
		log.info("get all lesson history result. resultMap={}", resultMap);
		return resultMap;
	}
	
	public List<LessonHistoryInfo> getAllMemberLessonHistoryInfoByDateTime(long userId, LocalDate datetime) {
		LocalDateTime startTime = LocalDateTime.of(datetime.getYear(), datetime.getMonth(), datetime.getDayOfMonth(), 0, 0);
		LocalDateTime endTime = LocalDateTime.of(datetime.getYear(), datetime.getMonth(), datetime.getDayOfMonth()+1, 0, 0).minusSeconds(1);
		log.info("get all lesson history. startTime={} ~ endTime={}", startTime, endTime);
		
		List<LessonHistory> lessonHistoryList = findRangedHistoryByEmployeeId(userId, startTime, endTime);
		log.info("getAllEmployeeLessonHistoryInfoByDateTime. resultMap={}", lessonHistoryList);
		
		if(lessonHistoryList.isEmpty()) {
			throw new NoResultByIdException(ServiceType.MEMBER.getName(), 0, ServiceType.LESSON_HISTORY.getName()); // TODO
		}
		
		return lessonHistoryMapper.mapToLessonHistoryInfoList(lessonHistoryList);
	}
}
