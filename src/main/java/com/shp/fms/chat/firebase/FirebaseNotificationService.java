package com.shp.fms.chat.firebase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.shp.fms.chat.ChatMessage;
import com.shp.fms.chat.ChatMessage.MessageType;
import com.shp.fms.chat.ChatUser;
import com.shp.fms.chat.ChatUserRepository;
import com.shp.fms.chat.mongo.ChatRoomDocument;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseNotificationService {

	private final FirebaseMessaging firebaseMessaging;
	
    private final ChatUserRepository userRepository;
	
	public void sendNotificationByToken(ChatMessage chatMessage, ChatRoomDocument roomDoc) {
		log.info("######## sendNotificationByToken. message={}, roomDoc={}", chatMessage, roomDoc);
		String roomId = chatMessage.getRoomId();
		
		// Firebase sub/unsub?
		// TODO 방에 있는 모든 유저 정보를 가져와서 token 설정 하고 status 상태 확인해서 메세지 보낼지 말지 확인
		List<ChatUser> chatUserList = userRepository.getChatUserList(roomId, roomDoc.getEmployeeId(), roomDoc.getMemberId());
		List<Message> messageList = new ArrayList<>();
		
		Notification notification = Notification.builder()
				.setTitle(chatMessage.getSender())
				.setBody(chatMessage.getContent())
				.build();
		
		for(ChatUser user : chatUserList) {
			if(!user.getId().equals(chatMessage.getSender()) && user.getStatus().equals(MessageType.LEAVE)) {
				Message message = Message.builder()
						.setToken(user.getDeviceToken())
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
