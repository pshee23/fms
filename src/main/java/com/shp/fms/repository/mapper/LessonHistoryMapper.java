package com.shp.fms.repository.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.shp.fms.model.LessonHistoryInfo;
import com.shp.fms.model.entity.Lesson;
import com.shp.fms.model.entity.LessonHistory;
import com.shp.fms.model.entity.Member;
import com.shp.fms.model.request.LessonHistoryRequestBody;

@Component
public class LessonHistoryMapper {

	public LessonHistoryInfo mapToLessonHistoryInfo(LessonHistory lessonListory) {
		return LessonHistoryInfo.builder()
				.lessonHistoryId(lessonListory.getId())
				.startDateTime(lessonListory.getStartDateTime())
				.endDateTime(lessonListory.getEndDateTime())
				.status(lessonListory.getStatus())
				.build();
	}
	
	public LessonHistory mapToLessonHistory(LessonHistoryRequestBody registerInfo, Member employee, Member member, Lesson lesson) {
		LessonHistory lessonHistory = new LessonHistory();
		lessonHistory.setStartDateTime(registerInfo.getStartDateTime());
		lessonHistory.setEndDateTime(registerInfo.getEndDateTime());
		lessonHistory.setStatus(registerInfo.getStatus());
		return lessonHistory;
	}

	public List<LessonHistoryInfo> mapToLessonHistoryInfoList(List<LessonHistory> lessonHistoryList) {
		List<LessonHistoryInfo> lessonHistoryInfoList = new ArrayList<>();

		for(LessonHistory lessonHistory : lessonHistoryList) {
			LessonHistoryInfo lessonHistoryInfo = mapToLessonHistoryInfo(lessonHistory);
			lessonHistoryInfoList.add(lessonHistoryInfo);
		}
		
		return lessonHistoryInfoList;
	}
}
