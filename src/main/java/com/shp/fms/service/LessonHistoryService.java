package com.shp.fms.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shp.fms.common.exception.NoResultByIdException;
import com.shp.fms.common.type.ServiceType;
import com.shp.fms.model.LessonHistoryInfo;
import com.shp.fms.model.entity.Employee;
import com.shp.fms.model.entity.Lesson;
import com.shp.fms.model.entity.LessonHistory;
import com.shp.fms.model.entity.Member;
import com.shp.fms.model.request.LessonHistoryRequestBody;
import com.shp.fms.repository.LessonHistoryRepository;
import com.shp.fms.repository.impl.LessonHistoryRepositoryImpl;
import com.shp.fms.repository.mapper.LessonHistoryMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class LessonHistoryService {
	
	private final LessonHistoryRepository lessonHistoryRepository;
	
	private final LessonHistoryRepositoryImpl lessonHistoryRepositoryImpl;
	
	private final EmployeeService employeeService;
	
	private final MemberService memberService;
	
	private final LessonService lessonService;
	
	private final LessonHistoryMapper lessonHistoryMapper;

	public void registerLessonHistory(LessonHistoryRequestBody registerInfo) {
		// TODO 조회 안되면 에러도 없이 무반응. 안드는 200 리턴됨..
		Employee employee = employeeService.getEmployeeById(registerInfo.getEmployeeId());
		Member member = memberService.getMemberById(registerInfo.getMemberId());
		Lesson lesson = lessonService.getLessonById(registerInfo.getLessonId());
		LessonHistory lessonHistory = lessonHistoryMapper.mapToLessonHistory(registerInfo, employee, member, lesson);
		log.info("#############. body={}", lessonHistory);
		LessonHistory saveResult = lessonHistoryRepository.save(lessonHistory);
		log.info("registered lesson-history. body={}", saveResult);
		// update Lesson
		lessonService.modifyLesson(registerInfo.getLessonId());
	}
	
	public Map<LocalDate, List<String>> getAllEmployeeLessonHistoryMarkerByDate(long employeeId, int year, int month) {
		LocalDateTime startTime = LocalDateTime.of(year, month, 1, 0, 0);
		LocalDateTime endTime = LocalDateTime.of(year, month, 1, 0, 0).plusMonths(1).minusSeconds(1);
		log.info("get all lesson history. startTime={} ~ endTime={}", startTime, endTime);
		List<LessonHistory> lessonHistoryList = lessonHistoryRepository.findAllByEmployee_EmployeeIdAndStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqualOrderByStartDateTimeAsc(employeeId, startTime, endTime);
		Map<LocalDate, List<String>> resultMap = new HashMap<>();
		for(LessonHistory history : lessonHistoryList) {
			log.info("######## {}", history.toString());
			LocalDate localDate = history.getStartDateTime().toLocalDate();
			List<String> stringList = resultMap.getOrDefault(localDate, new ArrayList<>());
			stringList.add(history.getLessonHistoryId().toString());
			resultMap.put(localDate, stringList);
			log.info("######## {}", resultMap);
		}
		return resultMap;
	}
	
	public List<LessonHistoryInfo> getTop10EmployeeLessonHistoryInfo(long employeeId) {
		log.info("get 10 lesson history.");
		List<LessonHistory> lessonHistoryList = lessonHistoryRepository.findTop10ByEmployee_EmployeeIdOrderByStartDateTimeAsc(employeeId);
		if(lessonHistoryList.isEmpty()) {
			throw new NoResultByIdException(ServiceType.MEMBER.getName(), 0, ServiceType.LESSON_HISTORY.getName()); // TODO
		}
		return lessonHistoryMapper.mapToLessonHistoryInfoList(lessonHistoryList);
	}
	
	public List<LessonHistoryInfo> getAllEmployeeLessonHistoryInfoByRange(long employeeId, int orderByTime, int offset, int limit, LocalDate startTime, LocalDate endTime) {
		log.info("get all lesson history. startTime={} ~ endTime={}", startTime, endTime);
		List<LessonHistory> lessonHistoryList = lessonHistoryRepositoryImpl.findFilteredOrderedSearchResults(employeeId, orderByTime, offset, limit, startTime, endTime);
		for(LessonHistory lh : lessonHistoryList) {
			log.info("@@@@@@@@@ {}", lh.getStartDateTime());
		}
		if(lessonHistoryList.isEmpty()) {
			throw new NoResultByIdException(ServiceType.MEMBER.getName(), 0, ServiceType.LESSON_HISTORY.getName()); // TODO
		}
		return lessonHistoryMapper.mapToLessonHistoryInfoList(lessonHistoryList);
	}
	
	public List<LessonHistoryInfo> getAllEmployeeLessonHistoryInfoByDateTime(long employeeId, LocalDate datetime) {
		LocalDateTime startTime = LocalDateTime.of(datetime.getYear(), datetime.getMonth(), datetime.getDayOfMonth(), 0, 0);
		LocalDateTime endTime = LocalDateTime.of(datetime.getYear(), datetime.getMonth(), datetime.getDayOfMonth()+1, 0, 0).minusSeconds(1);
		log.info("get all lesson history. startTime={} ~ endTime={}", startTime, endTime);
		List<LessonHistory> lessonHistoryList = lessonHistoryRepository.findAllByEmployee_EmployeeIdAndStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqualOrderByStartDateTimeAsc(employeeId, startTime, endTime);
		for(LessonHistory lh : lessonHistoryList) {
			log.info("@@@@@@@@@ {}", lh.getStartDateTime());
		}
		if(lessonHistoryList.isEmpty()) {
			throw new NoResultByIdException(ServiceType.MEMBER.getName(), 0, ServiceType.LESSON_HISTORY.getName()); // TODO
		}
		return lessonHistoryMapper.mapToLessonHistoryInfoList(lessonHistoryList);
	}
	
	public Map<LocalDate, List<String>> getAllMemberLessonHistoryMarkerByDate(long memberId, int year, int month) {
		LocalDateTime startTime = LocalDateTime.of(year, month, 1, 0, 0);
		LocalDateTime endTime = LocalDateTime.of(year, month, 1, 0, 0).plusMonths(1).minusSeconds(1);
		log.info("get all lesson history. startTime={} ~ endTime={}", startTime, endTime);
		List<LessonHistory> lessonHistoryList = lessonHistoryRepository.findAllByMember_MemberIdAndStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqualOrderByStartDateTimeAsc(memberId, startTime, endTime);
		Map<LocalDate, List<String>> resultMap = new HashMap<>();
		for(LessonHistory history : lessonHistoryList) {
			log.info("######## {}", history.toString());
			LocalDate localDate = history.getStartDateTime().toLocalDate();
			List<String> stringList = resultMap.getOrDefault(localDate, new ArrayList<>());
			stringList.add(history.getLessonHistoryId().toString());
			resultMap.put(localDate, stringList);
			log.info("######## {}", resultMap);
		}
		return resultMap;
	}
	
	public List<LessonHistoryInfo> getAllMemberLessonHistoryInfoByDateTime(long memberId, LocalDate datetime) {
		LocalDateTime startTime = LocalDateTime.of(datetime.getYear(), datetime.getMonth(), datetime.getDayOfMonth(), 0, 0);
		LocalDateTime endTime = LocalDateTime.of(datetime.getYear(), datetime.getMonth(), datetime.getDayOfMonth()+1, 0, 0).minusSeconds(1);
		log.info("get all lesson history. startTime={} ~ endTime={}", startTime, endTime);
		List<LessonHistory> lessonHistoryList = lessonHistoryRepository.findAllByMember_MemberIdAndStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqualOrderByStartDateTimeAsc(memberId, startTime, endTime);
		for(LessonHistory lh : lessonHistoryList) {
			log.info("@@@@@@@@@ {}", lh.getStartDateTime());
		}
		if(lessonHistoryList.isEmpty()) {
			throw new NoResultByIdException(ServiceType.MEMBER.getName(), 0, ServiceType.LESSON_HISTORY.getName()); // TODO
		}
		return lessonHistoryMapper.mapToLessonHistoryInfoList(lessonHistoryList);
	}

	public List<LessonHistory> getAllByEmployeeIdAndDate(long employeeId, LocalDateTime startDate, LocalDateTime endDate) {
		List<LessonHistory> lessonHistoryList = lessonHistoryRepository.findAllByEmployee_EmployeeIdAndStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqualOrderByStartDateTimeAsc(employeeId, startDate, endDate);
		if(lessonHistoryList.isEmpty()) {
			// TODO 다른 리턴 방식?
			throw new NoResultByIdException(ServiceType.EMPLOYEE.getName(), employeeId, ServiceType.LESSON_HISTORY.getName());
		}
		return lessonHistoryList;
	}
	
	public void changeLessonHistoryStatus(long lessonHistoryId, String status) {
		Optional<LessonHistory> lessonHistoryOptional = lessonHistoryRepository.findById(lessonHistoryId);
		if(lessonHistoryOptional.isEmpty()) {
			// TODO return error
			return;
		}
		LessonHistory lessonHistory = lessonHistoryOptional.get();
		lessonHistory.setStatus(status);
		lessonHistoryRepository.save(lessonHistory); // XXX 원래 자동으로 업데이트되는거 아닌가?
	}
}
