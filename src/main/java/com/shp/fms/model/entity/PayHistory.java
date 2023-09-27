package com.shp.fms.model.entity;

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
@Table(name = "PAY_HISTORY")
public class PayHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long payHistoryId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="EMPLOYEE_ID")
	private Employee employee;
	
	@Column(name="SALARY")
	private int salary;
	
	@Column(name="START_DATE")
	private LocalDateTime startTime;
	
	@Column(name="END_DATE")
	private LocalDateTime endTime;

	@Column(name="PAY_DATE_TIME")
	private LocalDateTime payDateTime;;
}
