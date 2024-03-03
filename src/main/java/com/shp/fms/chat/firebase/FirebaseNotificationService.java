package com.shp.fms.chat.firebase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.shp.fms.chat.ChatMessage;
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
		
		// Firebase sub/unsub?
		// TODO 방에 있는 모든 유저 정보를 가져와서 token 설정 하고 status 상태 확인해서 메세지 보낼지 말지 확인
		List<ChatUser> chatUserList = userRepository.getChatUserList(roomDoc.getEmployeeId(), roomDoc.getMemberId());
		List<Message> messageList = new ArrayList<>();
		for(ChatUser user : chatUserList) {
			Notification notification = Notification.builder()
					.setTitle(chatMessage.getSender())
					.setBody(chatMessage.getContent())
					.build();
			
			Message message = Message.builder()
					// TODO 상대방의 토큰에다가 전송해야함
					.setToken(user.getDeviceToken())
					.setNotification(notification)
					.build();
			messageList.add(message);
		}

		
		try {
			firebaseMessaging.sendEach(messageList);
//			firebaseMessaging.send(message);
			log.info("######## sendNotificationByToken SUCCESS");
		} catch (FirebaseMessagingException e) {
			log.error("######## sendNotificationByToken FAIL", e);
		}
	}
}
