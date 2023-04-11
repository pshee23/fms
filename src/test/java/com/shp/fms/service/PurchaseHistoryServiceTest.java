package com.shp.fms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shp.fms.controller.request.PurchaseHistoryRequestBody;
import com.shp.fms.model.PurchaseHistoryInfo;
import com.shp.fms.model.entity.Employee;
import com.shp.fms.model.entity.Member;
import com.shp.fms.model.entity.Product;
import com.shp.fms.model.entity.PurchaseHistory;
import com.shp.fms.repository.PurchaseHistoryRepository;
import com.shp.fms.repository.mapper.PurchaseHistoryMapper;

@ExtendWith(MockitoExtension.class)
class PurchaseHistoryServiceTest {

	@Mock
	PurchaseHistoryRepository purchaseRepository;
	
	@Mock
	PurchaseHistoryMapper purchaseMapper;
	
	@Mock
	ProductService productService;
	
	@Mock
	EmployeeService employeeService;
	
	@Mock
	MemberService memberService;
	
	PurchaseHistoryService purchaseService;
	
	@BeforeEach
	void setUp() {
		this.purchaseService = new PurchaseHistoryService(purchaseRepository, productService, employeeService, memberService, purchaseMapper);
	}
	
	@Test
	void testRegisterPurchaseHistory() {
		PurchaseHistoryRequestBody registerInfo = registerInfo();
		Product product = product();
		Employee employee = employee();
		Member member = member();
		PurchaseHistory purchaseHistory = purchaseHistory();
		PurchaseHistory purchaseReturnHistory = purchaseReturnHistory();
		PurchaseHistoryInfo info = productInfo();
		
		when(productService.getProductById(1l)).thenReturn(product);
		when(employeeService.getEmployeeById(1l)).thenReturn(employee);
		when(memberService.getMemberById(1l)).thenReturn(member);
		when(purchaseMapper.mapToPurchaseHistory(registerInfo, product, employee, member)).thenReturn(purchaseHistory);
		when(purchaseRepository.save(purchaseHistory)).thenReturn(purchaseReturnHistory);
		when(purchaseMapper.mapToPurchaseHistoryInfo(purchaseReturnHistory)).thenReturn(info);
		
		PurchaseHistoryInfo returnInfo = purchaseService.registerPurchaseHistory(registerInfo);
		
		assertEquals(info, returnInfo);
	}

	@Test
	void testGetAllPurchaseHistoryInfo() {
		fail("Not yet implemented");
	}
	
	private PurchaseHistoryRequestBody registerInfo() {
		PurchaseHistoryRequestBody registerInfo = new PurchaseHistoryRequestBody();
		registerInfo.setEmployeeId(1l);
		registerInfo.setEndDate(null);
		registerInfo.setMemberId(1l);
		registerInfo.setProductId(1l);
		registerInfo.setStartDate(null);
		registerInfo.setPurchaseDate(null);
		return registerInfo;
	}
	
	private PurchaseHistory purchaseHistory() {
		PurchaseHistory purchaseHistory = new PurchaseHistory();
		purchaseHistory.setEmployee(null);
		purchaseHistory.setMember(null);
		purchaseHistory.setProduct(null);
		purchaseHistory.setEndDate(null);
		purchaseHistory.setPurchaseDate(null);
		purchaseHistory.setStartDate(null);
		return purchaseHistory;
	}
	
	private PurchaseHistory purchaseReturnHistory() {
		PurchaseHistory purchaseHistory = new PurchaseHistory();
		purchaseHistory.setPurchaseId(1l);
		purchaseHistory.setEmployee(null);
		purchaseHistory.setMember(null);
		purchaseHistory.setProduct(null);
		purchaseHistory.setEndDate(null);
		purchaseHistory.setPurchaseDate(null);
		purchaseHistory.setStartDate(null);
		return purchaseHistory;
	}
	
	private PurchaseHistoryInfo productInfo() {
		PurchaseHistoryInfo info = PurchaseHistoryInfo.builder()
				.memberId(1l)
				.build();
		return info;
	}

	private Product product() {
		Product product = new Product();
		product.setBranch(null);
		product.setCompany(null);
		product.setEndDate(null);
		product.setName("name");
		product.setPrice(1000);
		product.setProductId(1l);
		product.setStartDate(null);
		product.setType("PT");
		return product;
	}
	
	private Employee employee() {
		Employee employee = new Employee();
		employee.setEmployeeId(1l);
		employee.setAddress("address");
		employee.setBranch(null);
		employee.setName("name");
		employee.setPhoneNumber("phone");
		employee.setRole(null);
		employee.setStatus(null);
		return employee;
	}
	
	private Member member() {
		Member member = new Member();
		member.setBranch(null);
		member.setEmployee(null);
		member.setAddress("address");
		member.setMemberId(1l);
		member.setName("name");
		member.setPhoneNumber("phone");
		return member;
	}
}
