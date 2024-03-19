package com.shp.fms.chat;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatUserController {
	
	private final ChatUserRepository chatUserRepository;
	
	/*
	 * 앱에 로그인 시 user status 정보 갱신
	 */
	@PutMapping("room/{roomId}/user")
	@ResponseBody
	public ResponseEntity<String> updateDevice(@PathVariable("roomId") String roomId, @RequestBody ChatUser chatUser) {
		log.info("######## updateDevice. body={}", chatUser);
		chatUserRepository.updateUserState(roomId, chatUser.getId(), chatUser.getStatus());
		return ResponseEntity.ok().build();
	}
}
