package com.shpfms.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "MEMBER")
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;
	
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
	
	// TODO table 관계성
}
