package com.shp.fms.chat;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.shp.fms.chat.firebase.FirebaseNotificationService;
import com.shp.fms.chat.mongo.ChatRoomDocument;
import com.shp.fms.service.MongoDbService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * Redis 발생 서비스
 * 채팅방에 입장해서 메시지를 작성하면 해당 메시지를 redis topic에 발행하는 서비스 
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisMessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    
    private final MongoDbService mongoService;
    
    private final FirebaseNotificationService notificationService;
    
    public void publish(ChannelTopic topic, ChatMessage message){
        redisTemplate.convertAndSend(topic.getTopic(), message);
        
        mongoService.registerChatMessage(message.getRoomId(), message.getSender(), message.getContent());
        
        if(ChatMessage.MessageType.CHAT.equals(message.getType())) {
        	ChatRoomDocument roomDoc = mongoService.findChatRoomByRoomId(message.getRoomId());
        	if(roomDoc != null) {
        		notificationService.sendNotificationByToken(message, roomDoc);        		
        	}
	    }
    }
}
