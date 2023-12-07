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

import org.hibernate.annotations.ColumnDefault;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "LESSON")
public class Lesson {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long lessonId;
	
	@Column(name="LESSON_NAME")
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MEMBER_ID")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="EMPLOYEE_ID")
	private Employee employee;

	@Column(name="START_DATETIME")
	private LocalDateTime startDateTime;
	
	@Column(name="END_DATETIME")
	private LocalDateTime endDateTime;
	
	@Column(name="TOTAL_COUNT")
	private int totalCount;
	
	@Column(name="CURRENT_COUNT")
	@ColumnDefault("0")
	private int currentCount;
	
	@Column(name="UPDATE_DATETIME")
	private LocalDateTime updateDateTime = LocalDateTime.now();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="BRANCH_ID")
	private Branch branch;
	
	public void setCurrentCount() {
		this.currentCount += 1;
	}
	
	public void updateDateTime() {
		this.updateDateTime = LocalDateTime.now();
	}
	
}
