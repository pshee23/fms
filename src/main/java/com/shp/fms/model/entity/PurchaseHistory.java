package com.shp.fms.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PURCAHSE_HISTORY")
public class PurchaseHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long purchaseId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PRODUCT_ID")
	private Product product;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="DISCOUNT_ID")
//	private Discount discount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="EMPLOYEE_ID")
	private Employee employee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MEMBER_ID")
	private Member member;
	
	@Column(name="PURCHASE_DATE")
	private LocalDateTime purchaseDate;
	
	@Column(name="START_DATE")
	private LocalDate startDate;
	
	@Column(name="END_DATE")
	private LocalDate endDate;

}
