package com.shp.fms.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Membership")
public class Membership {

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MEMBER_ID")
	private Member member;

	@Column(name="START_DATE")
	private LocalDate startDate;
	
	@Column(name="END_DATE")
	private LocalDate endDate;
	
	@Builder
	public Membership(Member member, LocalDate startDate, LocalDate endDate) {
		this.member = member;
		this.startDate = startDate;
		this.endDate = endDate;
	}

}
