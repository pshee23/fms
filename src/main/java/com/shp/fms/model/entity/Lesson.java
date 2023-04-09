package com.shp.fms.model.entity;

import java.time.LocalDate;
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
	private Long lessonId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MEMBER_ID")
	private Member member;

	@Column(name="START_DATE")
	private LocalDate startDate;
	
	@Column(name="LAST_LESSON")
	private LocalDateTime lastLessonTime;
	
	@Column(name="TOTAL_COUNT")
	private int totalCount;
	
	@Column(name="CURRENT_COUNT")
	@ColumnDefault("0")
	private int currentCount;
	
	public void setStartDate(LocalDateTime lastLessonTime) {
		if(this.startDate == null) {
			this.startDate = lastLessonTime.toLocalDate();
		}
	}
	
	public void setCurrentCount() {
		this.currentCount += 1;
	}
	
}
