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

import com.shp.fms.chat.model.ChatRoom;
import com.shp.fms.chat.model.request.CreateChatRoomRequest;
import com.shp.fms.service.MongoDbService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {
	
	private final MongoDbService mongoService;
	
	/*
	 * 1. 기본적으로 수업 신규 등록하면 create chat room & enter chat room
	 * 2. 앱 삭제 혹은 탈퇴 시 chat room 삭제
	 * 3. 혹시 방이 제대로 생성되지 않을수도 있으니 수동으로 방을 만들수 있도록 한다(flutter에서 추가)
	 */
	
	/*
	 * userId로 생성된 모든 채팅방 조회
	 * @methodName : getRoomListByUserId
	 */
	@GetMapping("/room/list/user/{userId}")
    @ResponseBody
    public ResponseEntity<List<ChatRoom>> getRoomListByUserId(@PathVariable String userId) {
		log.info("[REQ] get roomList by userId. userId={}", userId);
		List<ChatRoom> resultList = mongoService.getRoomListByUserId(userId);
        return ResponseEntity.ok(resultList);
    }
	
	/*
	 * 채팅방 신규 생성
	 * @methodName : createNewRoom
	 */
	@PostMapping("/room")
	@ResponseBody
	public ResponseEntity<ChatRoom> createNewRoom(@RequestBody CreateChatRoomRequest createRequest) {
		log.info("[REQ] CreateRoom. requestBody={}", createRequest);
		String roomId = mongoService.makeChatRoom(createRequest);
		if(roomId == null) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok().build();
	}
}
