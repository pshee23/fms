package com.shp.fms.chat.firebase;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.shp.fms.chat.ChatMessage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FirebaseNotificationService {

	private final FirebaseMessaging firebaseMessaging;
	
	
	public String sendNotificationByToken(ChatMessage chatMessage) {
		// TODO 유저 정보 or Firebase Token 존재 여부 체크
		// token은 언제 어디서 갱신, 어떤 방식으로 저장할것인지?
		// https://kbwplace.tistory.com/179
		Notification notification = Notification.builder()
				.setTitle(null)
				.setBody(null)
				.build();
		
		Message message = Message.builder()
				.setToken(null)
				.setNotification(notification)
				.build();
		
		try {
			firebaseMessaging.send(message);
			return "알림 보내기 성공.";
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
			return "알림 보내기 실패.";
		}
	}
}
