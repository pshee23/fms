package com.shp.fms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.shp.fms.chat.ChatMessage;
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
		try {
			ChatRoomDocument result = chatRoomRepository.insert(document);
			log.info("######### registerChatRoom. => {}", result);
			return result.get_id();
		} catch(OptimisticLockingFailureException ex) {
			log.error("######### registerChatRoom fail. doc={}", document, ex);
			return null;
		}
	}
	
	public ChatRoomDocument findChatRoomByRoomId(String roomId) {
		return chatRoomRepository.findById(roomId).get();
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
	
	public void deleteChatRoom(String roomId) {
		chatRoomRepository.deleteById(roomId);
		log.info("######### deleteChatRoomName by id. => {}", roomId);
	}
	
	public void registerChatUser(String roomId, String userName) {
		ChatUserDocument document = ChatUserDocument.builder()
				.roomId(roomId)
				.userName(userName).build();
		ChatUserDocument result = chatUserRepository.insert(document);
		log.info("######### registerChatUser. => {}", result);
	}
	
	public void deleteChatUser(String roomId, String userName) {
		chatUserRepository.deleteByRoomIdAndUserName(roomId, userName);
		log.info("######### deleteChatUser by roomId. => {}", roomId);
	}
	
	public void registerChatMessage(String roomId, String userName, String message) {
		ChatMessageDocument document = ChatMessageDocument.builder().roomId(roomId).userName(userName).message(message).build();
		ChatMessageDocument result = chatMessageRepository.insert(document);
		log.info("######### registerChatMessage. => {}", result);
	}
	
	public List<ChatMessage> getChatMessages(String roomId) {
		List<ChatMessageDocument> resultList = chatMessageRepository.findAllByRoomId(roomId);
		log.info("######### getChatMessages. => {}", resultList);
		List<ChatMessage> chatList = new ArrayList();
		for(ChatMessageDocument doc : resultList) {
			ChatMessage message = new ChatMessage();
			message.setRoomId(roomId);
			message.setSender(doc.getUserName());
			message.setContent(doc.getMessage());
			chatList.add(message);
		}
		return chatList;
	}
}
