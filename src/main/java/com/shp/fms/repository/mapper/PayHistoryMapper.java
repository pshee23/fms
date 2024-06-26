package com.shp.fms.repository.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.shp.fms.model.PayHistoryInfo;
import com.shp.fms.model.entity.Member;
import com.shp.fms.model.entity.PayHistory;
import com.shp.fms.model.request.PayHistoryRequestBody;

@Component
public class PayHistoryMapper {

	public PayHistoryInfo mapToPayHistoryInfo(PayHistory payHistory) {
		return PayHistoryInfo.builder()
				.payHistoryId(payHistory.getPayHistoryId())
				.employeeId(payHistory.getEmployee().getId())
				.payDateTime(payHistory.getPayDateTime())
				.startTime(payHistory.getStartTime())
				.endTime(payHistory.getEndTime())
				.salary(payHistory.getSalary())
				.build();
	}
	
	public PayHistory mapToPayHistory(PayHistoryRequestBody registerInfo, Member employee, int salary) {
		PayHistory payHistory = new PayHistory();
		payHistory.setEmployee(employee);
		payHistory.setPayDateTime(registerInfo.getPayDateTime());
		payHistory.setStartTime(registerInfo.getStartTime());
		payHistory.setEndTime(registerInfo.getEndTime());
		payHistory.setSalary(salary);
		return payHistory;
	}
	
	public List<PayHistoryInfo> mapToPayHistoryInfoList(List<PayHistory> payHistoryList) {
		List<PayHistoryInfo> payHistoryInfoList = new ArrayList<>();
		
		for(PayHistory payHistory : payHistoryList) {
			PayHistoryInfo payHistoryInfo = mapToPayHistoryInfo(payHistory);
			payHistoryInfoList.add(payHistoryInfo);
		}
		
		return payHistoryInfoList;
	}
}
