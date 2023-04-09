package com.shp.fms.controller.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shp.fms.model.LessonInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonResponse extends CommonResponse {
	private List<LessonInfo> lessonInfoList = new ArrayList<>();
	
	public LessonResponse successResponse(LessonInfo lessonInfo) {
		this.lessonInfoList.add(lessonInfo);
		successResponse(this.lessonInfoList);
		return this; // TODO test
	}
	
	public LessonResponse successResponse(List<LessonInfo> lessonInfoList) {
		LessonResponse lessonResponse = setResponse(true);
		lessonResponse.setLessonInfoList(lessonInfoList);
		return lessonResponse;
	}
	
	private LessonResponse setResponse(boolean isSuccess) {
		CommonResponse commonResponse = super.successResponse();
		if(!isSuccess) {
			commonResponse = super.failResponse();
		}
		
		LessonResponse lessonResponse = new LessonResponse();
		lessonResponse.setCode(commonResponse.code);
		lessonResponse.setMessage(commonResponse.message);
		return lessonResponse;
	}
}
