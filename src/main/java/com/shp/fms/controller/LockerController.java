package com.shp.fms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shp.fms.controller.request.LockerRequestBody;
import com.shp.fms.controller.response.LockerResponse;
import com.shp.fms.model.LockerInfo;
import com.shp.fms.service.LockerService;

import lombok.AllArgsConstructor;

@RequestMapping("/locker")
@RestController
@AllArgsConstructor
public class LockerController {
	
	private final LockerService lockerService;
	
	@PostMapping
	public ResponseEntity<LockerResponse> registerLocker(@RequestBody LockerRequestBody requestBody) {
		LockerInfo lockerInfo = lockerService.registerLocker(requestBody);
		return ResponseEntity.ok(new LockerResponse().successResponse(lockerInfo));
	}
	
	@PutMapping("/{lockerId}")
	public ResponseEntity<LockerResponse> modifyLocker(
			@PathVariable long lockerId,
			@RequestBody LockerRequestBody requestBody) {
		LockerInfo lockerInfo = lockerService.modifyLocker(lockerId, requestBody);
		return ResponseEntity.ok(new LockerResponse().successResponse(lockerInfo));
	}
	
	@DeleteMapping("/{lockerId}")
	public ResponseEntity<LockerResponse> deleteLocker(@PathVariable long lockerId) {
		boolean isDeleted = lockerService.deleteLocker(lockerId);
		return ResponseEntity.ok(new LockerResponse().deleteResponse(isDeleted));	
	}
	
	// TODO list by 5
	@GetMapping("/list")
	public ResponseEntity<LockerResponse> getAllLocker() {
		List<LockerInfo> lockerInfoList = lockerService.getAllLockerInfo();
		return ResponseEntity.ok(new LockerResponse().successResponse(lockerInfoList));
	}
	
	@GetMapping("/list/{lockerId}")
	public ResponseEntity<LockerResponse> getLockerById(@PathVariable long lockerId) {
		LockerInfo lockerInfo = lockerService.getLockerInfoById(lockerId);
		return ResponseEntity.ok(new LockerResponse().successResponse(lockerInfo));
	}
}
