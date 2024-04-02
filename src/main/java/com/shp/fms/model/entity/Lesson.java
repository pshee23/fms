package com.shp.fms.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "LESSON")
//@IdClass(EmployeeMemberId.class)
public class Lesson {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LESSON_ID")
	private Long id;
	
	@Column(name="LESSON_NAME")
	private String name;
	
//	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MEMBER_ID")
	private Member member;
	
//	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="EMPLOYEE_ID")
	private Member employee;
	
	@OneToMany(mappedBy="lesson")
	private List<LessonHistory> historyList = new ArrayList<>();

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
	
	public void setCurrentCount() {
		this.currentCount += 1;
	}
	
	public void updateDateTime() {
		this.updateDateTime = LocalDateTime.now();
	}
	
}
