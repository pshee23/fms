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
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
		name = "LESSON_HISTORY",
		uniqueConstraints= {
				@UniqueConstraint(
						name="LESSON-HISTORY_MEMBER_AND_DATETIME",
						columnNames={"MEMBER_ID","START_DATETIME"}
				)
		}
)
public class LessonHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lessonHistoryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="EMPLOYEE_ID")
	private Employee employee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MEMBER_ID")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LESSON_ID")
	private Lesson lesson;
	
	@Column(name="START_DATETIME")
	private LocalDateTime startDateTime;
	
	@Column(name="END_DATETIME")
	private LocalDateTime endDateTime;
	
	@Column(name="STATUS")
	private String status;

}
