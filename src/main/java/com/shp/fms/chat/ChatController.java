package com.shp.fms.chat;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

	 private final RedisMessagePublisher redisPublisher;
	 private final ChatRoomRepository chatRoomRepository;
	
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
	         chatRoomRepository.enterChatRoom(message.getRoomId(), message.getSender());
	         message.setContent(message.getSender() + "님이 입장하셨습니다.");
	     } else if(ChatMessage.MessageType.LEAVE.equals(message.getType())) {
	    	 chatRoomRepository.leaveChatRoom(message.getRoomId(), message.getSender());
	     } else if(ChatMessage.MessageType.DELETE.equals(message.getType())) {
	    	 chatRoomRepository.deleteChatRoom(message.getRoomId());
	     }
	     // Websocket에 발행된 메시지를 redis로 발행한다(publish)
	     ChannelTopic topic = chatRoomRepository.getTopic(message.getRoomId());
	     if(topic == null) {
	    	 log.error("ChannelTopic Error??. message={}", message);
	     } else {
	    	 redisPublisher.publish(topic, message);	    	 
	     }
	 }
}
