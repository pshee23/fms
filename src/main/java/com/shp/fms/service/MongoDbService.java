package com.shp.fms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shp.fms.chat.mongo.ChatMessageDocument;
import com.shp.fms.chat.mongo.ChatRoomDocument;
import com.shp.fms.chat.mongo.ChatUserDocument;
import com.shp.fms.chat.mongo.MongoChatMessageRepository;
import com.shp.fms.chat.mongo.MongoChatRoomRepository;
import com.shp.fms.chat.mongo.MongoChatUserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MongoDbService {
	
	private final MongoChatRoomRepository chatRoomRepository;
	
	private final MongoChatUserRepository chatUserRepository;
	
	private final MongoChatMessageRepository chatMessageRepository;

	public String registerChatRoom(String name) {
		ChatRoomDocument document = ChatRoomDocument.builder().name(name).build();
		ChatRoomDocument result = chatRoomRepository.save(document);
		log.info("######### registerChatRoom. => {}", result);
		return result.get_id();
	}
	
	public void findChatRoom() {
		log.info("######### registerChatRoom. => {}", chatRoomRepository.findAll());
	}
	
	public void findChatRoom(String roomId) {
		log.info("######### registerChatRoom. => {}", chatRoomRepository.findById(roomId));	
	}
	
	public void changeChatRoomName(String roomId, String name) {
		Optional<ChatRoomDocument> document = chatRoomRepository.findById(roomId);
		if(document.isEmpty()) {
			log.info("######### no chat room found. {}", roomId);
			return;
		}
		ChatRoomDocument result = document.get();
		result.setName(name);
		chatRoomRepository.save(result);
		log.info("######### registerChatRoom. => {}", result);
	}
	
	public void deleteChatRoom(String roomId) {
		chatRoomRepository.deleteById(roomId);
		log.info("######### deleteChatRoomName by id. => {}", roomId);
	}
	
	public List<ChatUserDocument> findAllChatRoomUser(String roomId) {
		List<ChatUserDocument> userList = chatUserRepository.findAllByRoomId(roomId);
		log.info("######### findAllChatRoomUser by id. => {}", userList);
		return userList;
	}
	
	public void registerChatUser(String roomId, String userName) {
		ChatUserDocument document = ChatUserDocument.builder().roomId(roomId).userName(userName).build();
		ChatUserDocument result = chatUserRepository.save(document);
		log.info("######### registerChatUser. => {}", result);
	}
	
	public void deleteChatUser(String roomId, String userName) {
		chatUserRepository.deleteByRoomIdAndUserName(roomId, userName);
		log.info("######### deleteChatUser by id. => {}", roomId);
	}
	
	public void registerChatMessage(String roomId, String userName, String message) {
		ChatMessageDocument document = ChatMessageDocument.builder().roomId(roomId).userName(userName).message(message).build();
		ChatMessageDocument result = chatMessageRepository.save(document);
		log.info("######### registerChatMessage. => {}", result);
	}
}
