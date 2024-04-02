package com.shp.fms.chat.firebase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.shp.fms.chat.model.ChatMessage;
import com.shp.fms.chat.model.ChatMessage.MessageType;
import com.shp.fms.chat.model.ChatUserStatus;
import com.shp.fms.chat.mongo.document.ChatRoomDocument;
import com.shp.fms.chat.mongo.document.ChatUserDocument;
import com.shp.fms.service.MongoDbService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseNotificationService {

	private final FirebaseMessaging firebaseMessaging;
	
    private final MongoDbService mongoService;
	
	public void sendNotificationByToken(ChatMessage chatMessage, ChatRoomDocument roomDoc) {
		log.info("######## sendNotificationByToken. message={}, roomDoc={}", chatMessage, roomDoc);
		String roomId = chatMessage.getRoomId();
		
		// Firebase sub/unsub?
		// TODO 방에 있는 모든 유저 정보를 가져와서 token 설정 하고 status 상태 확인해서 메세지 보낼지 말지 확인
		ChatRoomDocument roomDocument = mongoService.findChatRoomByRoomId(roomId);
		
		Notification notification = Notification.builder()
				.setTitle(chatMessage.getSender())
				.setBody(chatMessage.getContent())
				.build();
		
		List<Message> messageList = new ArrayList<>();
		for(Entry<String, ChatUserStatus> user : roomDocument.getUserList().entrySet()) {
			if(!user.getKey().equals(chatMessage.getSender()) && user.getValue().getStatus().equals(MessageType.LEAVE)) {
				ChatUserDocument userDocument = mongoService.findChatUserDocByUserId(user.getKey());
				Message message = Message.builder()
						.setToken(userDocument.getDeviceToken())
						.setNotification(notification)
						.build();
				messageList.add(message);
			}
		}
		if(!messageList.isEmpty()) {
			try {
				firebaseMessaging.sendEach(messageList);
			} catch (FirebaseMessagingException e) {
				log.error("######## sendNotificationByToken FAIL", e);
			}	
		}
	}
}
