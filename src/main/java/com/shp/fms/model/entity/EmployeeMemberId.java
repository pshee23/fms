package com.shp.fms.model.entity;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeMemberId implements Serializable {
	private static final long serialVersionUID = -4566233951832799839L;
	
	@Column(name="member")
	private Long member;
	
	@Column(name="employee")
	private Long employee;
}
