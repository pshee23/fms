package com.shp.fms.chat;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shp.fms.chat.model.request.UpdateChatUserStatusRequest;
import com.shp.fms.service.MongoDbService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatUserController {
	
	private final MongoDbService mongoService;
	
	/*
	 * 앱에 로그인 시 user status 정보 갱신
	 */
	@PutMapping("room/{roomId}/user/{userId}")
	@ResponseBody
	public ResponseEntity<String> updateDevice(
			@PathVariable("roomId") String roomId, 
			@PathVariable("userId") String userId, 
			@RequestBody UpdateChatUserStatusRequest statusRequest) {
		log.info("######## updateDevice. body={}", statusRequest);
		mongoService.updateChatUserStatus(roomId, userId, statusRequest.getStatus());
		return ResponseEntity.ok().build();
	}
}
