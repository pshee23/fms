package com.shp.fms.chat;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.shp.fms.chat.firebase.FirebaseNotificationService;
import com.shp.fms.chat.model.ChatMessage;
import com.shp.fms.chat.model.ChatMessage.MessageType;
import com.shp.fms.chat.mongo.document.ChatRoomDocument;
import com.shp.fms.service.MongoDbService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

	 private final RedisMessagePublisher redisPublisher;
	 private final ChatRoomRepository roomRepository;

	 private final MongoDbService mongoService;
	 private final FirebaseNotificationService notificationService;
	
	 // XXX
	 // exists CHAT_ROOM
	 // hkeys CHAT_ROOM
	 // subscribe {roomId}
	 /**
	  * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
	  */
	 @MessageMapping("/chat/message")
	 public void message(ChatMessage message) {
		 log.info("######## message. charMessage={}", message);
		 String roomId = message.getRoomId();
		 String sender = message.getSender();
		 MessageType type = message.getType();
		
		 // Websocket에 발행된 메시지를 redis로 발행한다(publish)
		 ChannelTopic topic = roomRepository.getTopic(roomId);
	     if(topic == null) {
	    	 log.error("ChannelTopic Error??. message={}", message);
	     } else {
			 if (ChatMessage.MessageType.JOIN.equals(type)) { // 채팅방 입장 (Foreground)
				 mongoService.enterChatRoom(roomId, sender);
		     } else if(ChatMessage.MessageType.LEAVE.equals(type)) { // 채팅방 퇴장 (Background)
		    	 mongoService.leaveChatRoom(roomId, sender);
		     } 
			 
	    	 redisPublisher.publish(topic, message);

	         mongoService.registerChatMessage(roomId, sender, message.getContent());
	         
	         if(ChatMessage.MessageType.CHAT.equals(type)) {
	         	ChatRoomDocument roomDoc = mongoService.findChatRoomByRoomId(roomId);
	         	if(roomDoc != null) {
	         		notificationService.sendNotificationByToken(message, roomDoc);        		
	         	}
	 	    }
	     }
	 }
}
