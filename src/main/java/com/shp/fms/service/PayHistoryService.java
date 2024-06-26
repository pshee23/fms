//package com.shp.fms.service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.shp.fms.common.exception.NoDataReturnedException;
//import com.shp.fms.common.type.ServiceType;
//import com.shp.fms.model.PayHistoryInfo;
//import com.shp.fms.model.entity.Lesson;
//import com.shp.fms.model.entity.LessonHistory;
//import com.shp.fms.model.entity.Member;
//import com.shp.fms.model.entity.PayHistory;
//import com.shp.fms.model.request.PayHistoryRequestBody;
//import com.shp.fms.repository.PayHistoryRepository;
//import com.shp.fms.repository.mapper.PayHistoryMapper;
//
//import lombok.AllArgsConstructor;
//
//@Service
//@AllArgsConstructor
//public class PayHistoryService {
//	
//	private final PayHistoryRepository payHistoryRepository;
//
//	private final ProductService productService;
//	
//	private final MemberService memberService;
//	
//	private final LessonHistoryService lessonHistoryService;
//	
//	private final PurchaseHistoryService purchaseHistoryService;
//	
//	private final PayHistoryMapper payHistoryMapper;
//	
//	public void registerPayHistory(PayHistoryRequestBody registerInfo) {
//		Member employee = memberService.getMemberById(registerInfo.getEmployeeId());
//
//		int salary = calculateSalary(employee.getMemberId(), registerInfo.getStartTime(), registerInfo.getEndTime());
//		
//		PayHistory payHistory = payHistoryMapper.mapToPayHistory(registerInfo, employee, salary);
//		
//		payHistoryRepository.save(payHistory);
//	}
//		
//	public List<PayHistoryInfo> getAllPayHistoryInfo() {
//		List<PayHistory> payHistoryList = payHistoryRepository.findAll();
//		if(payHistoryList.isEmpty()) {
//			throw new NoDataReturnedException(ServiceType.PAY_HISTORY.getName());
//		}
//		return payHistoryMapper.mapToPayHistoryInfoList(payHistoryList);
//	}
//	
//	private int calculateSalary(long employeeId, LocalDateTime startTime, LocalDateTime endTime) {
//		int salary = 0;
//		
//		List<LessonHistory> lessonHistoryList = lessonHistoryService.getAllByEmployeeIdAndDate(employeeId, startTime, endTime);
//		for(LessonHistory lessonHistory : lessonHistoryList) {
//			if(lessonHistory.getStatus().equals("CANCLE")) {
//				continue;
//			}
//			// TODO cal
//			long memberId = lessonHistory.getMember().getMemberId(); // XXX 하나의 MemberId가 아닌 memberList가 필요하게 되므로 중간 연결점이 필요함..?
//			Lesson lesson = lessonHistory.getLesson();
////			PurchaseHistory purchaseHistory = purchaseHistoryService.getAllPurchaseHistoryInfo()
//			
//			
//		}
//		
//		
//		return salary;
//	}
//}
