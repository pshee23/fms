package com.shp.fms.controller.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shp.fms.model.LessonHistoryInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonHistoryResponse extends CommonResponse {
	private List<LessonHistoryInfo> lessonHistoryInfoList;
	
	public LessonHistoryResponse successResponse(List<LessonHistoryInfo> lessonHistoryInfoList) {
		LessonHistoryResponse lessonResponse = setResponse(true);
		lessonResponse.setLessonHistoryInfoList(lessonHistoryInfoList);
		return lessonResponse;
	}
	
	private LessonHistoryResponse setResponse(boolean isSuccess) {
		CommonResponse commonResponse = super.successResponse();
		if(!isSuccess) {
			commonResponse = super.failResponse();
		}
		
		LessonHistoryResponse productResponse = new LessonHistoryResponse();
		productResponse.setCode(commonResponse.code);
		productResponse.setMessage(commonResponse.message);
		return productResponse;
	}
}
