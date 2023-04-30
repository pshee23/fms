package com.shp.fms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.shp.fms.model.entity.Employee;
import com.shp.fms.model.entity.Lesson;
import com.shp.fms.model.entity.LessonHistory;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class LessonHistoryRepositoryTest {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	LessonRepository lessonRepository;

	@Autowired
	LessonHistoryRepository repository;
	
	@BeforeEach
	public void setUp() {
		repository.save(firstLessonHistory());
	}
	
	@Test
	void testFindByEndDateBetween_EmployeeId() {
		LocalDateTime startDate = LocalDateTime.parse("2023-04-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LocalDateTime endDate = LocalDateTime.parse("2023-04-30 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		List<LessonHistory> lessonHistoryList = repository.findAllByEmployee_EmployeeIdAndLessonDateTimeBetween(1l, startDate, endDate);
		
		List<LessonHistory> dummyList = new ArrayList<>();
		dummyList.add(firstLessonHistory());
		
		assertEquals(dummyList.get(0).getLessonHistoryId(), lessonHistoryList.get(0).getLessonHistoryId());
	}
	
	private LessonHistory firstLessonHistory() {
		Employee employee = new Employee();
		employee.setEmployeeId(1l);
		employee.setName("emp1");
		employee.setPhoneNumber("111");
		employeeRepository.save(employee);

		Lesson lesson = new Lesson();
		lesson.setLessonId(1l);
		lessonRepository.save(lesson);

		LocalDateTime lessonDateTime = LocalDateTime.parse("2023-04-16 15:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LessonHistory lessonHistory = new LessonHistory();
		lessonHistory.setEmployee(employee);
		lessonHistory.setLesson(lesson);
		lessonHistory.setLessonDateTime(lessonDateTime);
		lessonHistory.setLessonHistoryId(1l);
		lessonHistory.setMember(null);
		lessonHistory.setStatus(null);
		return lessonHistory;
	}

}
