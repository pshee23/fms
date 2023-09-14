package com.shp.fms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shp.fms.model.entity.Colors;
import com.shp.fms.repository.ColorsRepository;

import lombok.AllArgsConstructor;

@RequestMapping("/colors")
@RestController
@AllArgsConstructor
public class ColorsController {
	
	private final ColorsRepository colorRepository;
	
	// TODO
	@GetMapping("")
	public ResponseEntity<Object> getAllColors() {
		List<Colors> colorsList = colorRepository.findAll();
		return ResponseEntity.ok(colorsList);
	}
	
}
