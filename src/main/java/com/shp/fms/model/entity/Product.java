package com.shp.fms.model.entity;

import java.time.LocalDate;

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
@Table(name = "PRODUCT")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="PRICE")
	private int price;
	
	@Column(name="TYPE")
	private String type;
	
	@Column(name="START_DATE")
	private LocalDate startDate;
	
	@Column(name="END_DATE")
	private LocalDate endDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COMPANY_ID")
	private Company company;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="BRANCH_ID")
	private Branch branch;
}
