package com.shp.fms.model.entity;

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
@Table(name = "COLORS")
public class Colors {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long colorId;
	
	@Column(name="COLOR_NAME")
	private String colorName;
	
	@Column(name="HEX_CODE")
	private String hexCode;	
}
