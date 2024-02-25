package com.shp.fms.chat;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {
	
	private final ChatRoomRepository chatRoomRepository;
	
	/*
	 * 1. 기본적으로 수업 신규 등록하면 create chat room & enter chat room
	 * 2. 앱 삭제 혹은 탈퇴 시 chat room 삭제
	 * 3. 혹시 방이 제대로 생성되지 않을수도 있으니 수동으로 방을 만들수 있도록 한다(flutter에서 추가)
	 */
	
	@GetMapping("/room/list/{userType}/{id}")
    @ResponseBody
    public ResponseEntity<List<ChatRoom>> findRoomById(@PathVariable String userType, @PathVariable String id) {
		log.info("######## find room by id. ");
		List<ChatRoom> resultList = chatRoomRepository.findRoomById(userType, id);
        return ResponseEntity.ok(resultList);
    }
	
	@PostMapping("/room")
	@ResponseBody
	public ResponseEntity<ChatRoom> createRoom(@RequestBody ChatRoom chatRoom) {
		log.info("######## createRoom. chatRoom={}", chatRoom);
		ChatRoom resultRoom = chatRoomRepository.createChatRoom(chatRoom);
		if(resultRoom == null) {
			return ResponseEntity.internalServerError().build();
		}
		// TODO check. 이렇게 각각 보내는게 맞나? zero에서 test 필요
		chatRoomRepository.enterChatRoom(resultRoom.getRoomId(), chatRoom.getEmployeeId());
		chatRoomRepository.enterChatRoom(resultRoom.getRoomId(), chatRoom.getMemberId());
		return ResponseEntity.ok(resultRoom);
	}
}
