package com.shp.fms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shp.fms.chat.ChatRoom;
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

	public String registerChatRoom(ChatRoom chatRoom) {
		ChatRoomDocument document = ChatRoomDocument.builder()
				.name(chatRoom.getName())
				.employeeId(chatRoom.getEmployeeId())
				.memberId(chatRoom.getMemberId()).build();
		ChatRoomDocument result = chatRoomRepository.save(document);
		log.info("######### registerChatRoom. => {}", result);
		return result.get_id();
	}
	
	public List<ChatRoom> findAllChatRoom() {
		List<ChatRoomDocument> documentList = chatRoomRepository.findAll();
		log.info("######### findAllChatRoom. => {}", documentList);
		return mapChatRoom(documentList);
	}
	
	public List<ChatRoom> findAllChatRoomByEmployeeId(String employeeId) {
		List<ChatRoomDocument> documentList = chatRoomRepository.findAllByEmployeeId(employeeId);
		log.info("######### findAllChatRoomByEmployeeId. => {}", documentList);
		return mapChatRoom(documentList);
	}
	
	public List<ChatRoom> findAllChatRoomByMemberId(String memberId) {
		List<ChatRoomDocument> documentList = chatRoomRepository.findAllByMemberId(memberId);
		log.info("######### findAllChatRoomByMemberId. => {}", documentList);
		return mapChatRoom(documentList);
	}
	
	private List<ChatRoom> mapChatRoom(List<ChatRoomDocument> documentList) {
		List<ChatRoom> roomList = new ArrayList<>();
		for(ChatRoomDocument document : documentList) {
			ChatRoom chatRoom = new ChatRoom();
			chatRoom.setRoomId(document.get_id());
			chatRoom.setName(document.getName());
			chatRoom.setEmployeeId(document.getEmployeeId());
			chatRoom.setMemberId(document.getMemberId());
			roomList.add(chatRoom);
		}
		return roomList;
	}
	
	
	public void findChatRoom(String roomId) {
		log.info("######### findChatRoom. => {}", chatRoomRepository.findById(roomId));	
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
		log.info("######### changeChatRoomName. => {}", result);
	}
	
	public void deleteChatRoom(String roomId) {
		chatRoomRepository.deleteById(roomId);
		log.info("######### deleteChatRoomName by id. => {}", roomId);
	}
	
	public List<ChatUserDocument> findAllChatRoomUser(String roomId) {
		List<ChatUserDocument> userList = chatUserRepository.findAllBy_id(roomId);
		log.info("######### findAllChatRoomUser by id. => {}", userList);
		return userList;
	}
	
	public void registerChatUser(String roomId, String userName) {
		ChatUserDocument document = ChatUserDocument.builder()._id(roomId).userName(userName).build();
		ChatUserDocument result = chatUserRepository.save(document);
		log.info("######### registerChatUser. => {}", result);
	}
	
	public void deleteChatUser(String roomId, String userName) {
		chatUserRepository.deleteBy_idAndUserName(roomId, userName);
		log.info("######### deleteChatUser by id. => {}", roomId);
	}
	
	public void registerChatMessage(String roomId, String userName, String message) {
		ChatMessageDocument document = ChatMessageDocument.builder()._id(roomId).userName(userName).message(message).build();
		ChatMessageDocument result = chatMessageRepository.save(document);
		log.info("######### registerChatMessage. => {}", result);
	}
}
