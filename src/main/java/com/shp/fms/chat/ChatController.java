package com.shp.fms.chat;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.shp.fms.chat.ChatMessage.MessageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

	 private final RedisMessagePublisher redisPublisher;
	 private final ChatRoomRepository roomRepository;
	 private final ChatUserRepository userRepository;
	
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
		 if (ChatMessage.MessageType.JOIN.equals(message.getType())) {
			 // 채팅방 입장 (Foreground)
	         roomRepository.enterChatRoom(message.getRoomId(), message.getSender());
	         
	         ChatUser chatUser = new ChatUser();
	         chatUser.setId(message.getSender());
	         chatUser.setStatus(MessageType.JOIN);
	         chatUser.setDeviceToken(message.getDeviceToken());
	         userRepository.updateUserState(chatUser);
	     } else if(ChatMessage.MessageType.LEAVE.equals(message.getType())) {
	    	 // 채팅방 퇴장 (Background)
	    	 roomRepository.leaveChatRoom(message.getRoomId(), message.getSender());
	    	 
	    	 ChatUser chatUser = new ChatUser();
	         chatUser.setId(message.getSender());
	         chatUser.setStatus(MessageType.LEAVE);
	         chatUser.setDeviceToken(message.getDeviceToken());
	    	 userRepository.updateUserState(chatUser);
	     } else {
	    	// Websocket에 발행된 메시지를 redis로 발행한다(publish)
		     ChannelTopic topic = roomRepository.getTopic(message.getRoomId());
		     if(topic == null) {
		    	 log.error("ChannelTopic Error??. message={}", message);
		     } else {
		    	 redisPublisher.publish(topic, message);	    	 
		     }	 
	     }
	 }
}
