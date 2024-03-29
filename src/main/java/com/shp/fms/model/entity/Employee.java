package com.shp.fms.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "EMPLOYEE")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeId;
	
	@Column(name="NAME", nullable=false)
	private String name;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="PHONE_NUMBER", nullable=false)
	private String phoneNumber;
	
	@Column(name="LOGIN_ID")
	private String loginId;
	
	@Column(name="LOGIN_PW")
	private String loginPw;
	
	@Column(name="ROLE")
	private String role;
	
	@Column(name="STATUS")
	private String status;
	
	// N:1
	@ManyToOne
	@JoinColumn(name="BRANCH_ID")
	private Branch branch;
}
