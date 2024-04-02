package com.shp.fms.chat;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shp.fms.chat.model.ChatMessage;
import com.shp.fms.service.MongoDbService;

import lombok.AllArgsConstructor;

@RequestMapping("/message")
@RestController
@AllArgsConstructor
public class MessageController {
	
	private final MongoDbService mongoDbService;
	

	@GetMapping("/room/{roomId}")
	public ResponseEntity<List<ChatMessage>> getAllMessages(@PathVariable String roomId) {
		List<ChatMessage> messageList = mongoDbService.getChatMessages(roomId);
		return ResponseEntity.ok(messageList);
	}
	
}
