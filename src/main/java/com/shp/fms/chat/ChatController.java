package com.shp.fms.chat;

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
//	 @MessageMapping("/chat.addUser")
//	 @SendTo("/topic/public")
	 public void message(ChatMessage message) {
		 log.info("######## message. charMessage={}", message);
	     if (ChatMessage.MessageType.JOIN.equals(message.getType())) {
	         chatRoomRepository.enterChatRoom(message.getRoomId());
	         message.setContent(message.getSender() + "님이 입장하셨습니다.");
	     }
	     // Websocket에 발행된 메시지를 redis로 발행한다(publish)
	     redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
	 }
	
//	@MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")
//    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
//		log.info("######## sendMessage. charMessage={}", chatMessage);
//        return chatMessage;
//    }

//    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/public")
//    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
//    	log.info("######## addUser. charMessage={}, headerAccessor={}", chatMessage, headerAccessor);
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//        return chatMessage;
//    }
}
