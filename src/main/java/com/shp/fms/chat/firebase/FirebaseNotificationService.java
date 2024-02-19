package com.shp.fms.chat.firebase;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.shp.fms.chat.ChatMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseNotificationService {

	private final FirebaseMessaging firebaseMessaging;
	
	public void sendNotificationByToken(ChatMessage chatMessage) {
		log.info("######## sendNotificationByToken. message={}", chatMessage);
		// TODO 유저 정보 or Firebase Token 존재 여부 체크
		// token은 언제 어디서 갱신, 어떤 방식으로 저장할것인지?
		// https://kbwplace.tistory.com/179
		Notification notification = Notification.builder()
				.setTitle(chatMessage.getSender())
				.setBody(chatMessage.getContent())
				.build();
		
		Message message = Message.builder()
				.setToken("eq5w-t__QJCmkY9IPV-ET8:APA91bEps-i1-vH0cOCaLQHLDm_Wpba6mHcRcrBhW-yJBTBiBO6ixVrLPUNLFthnAUa0wcOnn7WoDJ6zXno3DN-B8vrYEgo1ZJUY0DmAc7-Ph5jYPdt45LMxYrBxw13u625wfpMEelXH")
				.setNotification(notification)
				.build();
		
		try {
			firebaseMessaging.send(message);
			log.info("######## sendNotificationByToken SUCCESS");
		} catch (FirebaseMessagingException e) {
			log.error("######## sendNotificationByToken FAIL", e);
		}
	}
}
