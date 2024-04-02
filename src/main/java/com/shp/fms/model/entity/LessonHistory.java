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
@Table(name = "LESSON_HISTORY")
public class LessonHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LESSON_HISTORY_ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="LESSON_ID")
	private Lesson lesson;

	@Column(name="START_DATETIME")
	private LocalDateTime startDateTime;
	
	@Column(name="END_DATETIME")
	private LocalDateTime endDateTime;
	
	@Column(name="STATUS")
	private String status;

}
