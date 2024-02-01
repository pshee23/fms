package com.shp.fms.chat;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	@GetMapping("/rooms")
    @ResponseBody
    public ResponseEntity<List<ChatRoom>> room() {
		log.info("######## find all room");
        return ResponseEntity.ok(chatRoomRepository.findAllRoom());
    }
	
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestBody ChatRoom chatRoom) {
    	log.info("######## createRoom. chatRoom={}", chatRoom);
        return chatRoomRepository.createChatRoom(chatRoom);
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
    	log.info("######## roomInfo. roomId={}", roomId);
        return chatRoomRepository.findRoomById(roomId);
    }
    
    @DeleteMapping("/room/{roomId}")
    @ResponseBody
    public ResponseEntity<Object> deleteRoom(@PathVariable String roomId) {
    	log.info("######## deleteRoom. roomId={}", roomId);
    	chatRoomRepository.deleteChatRoom(roomId);
        return ResponseEntity.ok().build();
    }
}
