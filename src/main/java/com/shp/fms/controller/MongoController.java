package com.shp.fms.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shp.fms.chat.ChatRoom;
import com.shp.fms.service.MongoDbService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/chat")
@RestController
@AllArgsConstructor
public class MongoController {
	
	private final MongoDbService mongoService;
	
	// TODO register when user starts 1:1 pt
	@PostMapping("/room/create")
	public ResponseEntity<Object> registerChatRoom(@RequestBody ChatRoom chatRoom) {
		log.info("######### registerChatRoom. body={}", chatRoom);
		mongoService.registerChatRoom(chatRoom);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/room/list")
	public ResponseEntity<List<ChatRoom>> findAllChatRoomRepo() {
		return ResponseEntity.ok(mongoService.findAllChatRoom());
	}
	
	@GetMapping("/room/employee/{employeeId}")
	public ResponseEntity<List<ChatRoom>> findAllChatRoomByEmployeeId(@PathParam("employeeId") String employeeId) {
		return ResponseEntity.ok(mongoService.findAllChatRoomByEmployeeId(employeeId));
	}
	
	
	
	
	
	
	
	
	
	@GetMapping("/chat_room/repo/{id}")
	public void findChatRoomRepo(@PathVariable("id") String id) {
		mongoService.findChatRoom(id);
	}
	
	@PutMapping("/chat_room/repo/{id}/{name}")
	public void changeChatRoomNameRepo(@PathVariable("id") String id, @PathVariable("name") String name) {
		mongoService.changeChatRoomName(id, name);
	}
	
	@DeleteMapping("/chat_room/repo/{id}")
	public void deleteChatRoomNameRepo(@PathVariable("id") String id) {
		mongoService.deleteChatRoom(id);
	}
	
	@GetMapping("/chat_user/repo")
	public void findAllChatUserRepo(@PathVariable("id") String id) {
		mongoService.findAllChatRoomUser(id);
	}
}
