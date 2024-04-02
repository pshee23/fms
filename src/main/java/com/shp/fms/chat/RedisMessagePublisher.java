package com.shp.fms.chat;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.shp.fms.chat.model.ChatMessage;

import lombok.RequiredArgsConstructor;

/*
 * Redis 발생 서비스
 * 채팅방에 입장해서 메시지를 작성하면 해당 메시지를 redis topic에 발행하는 서비스 
 */
@Service
@RequiredArgsConstructor
public class RedisMessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    
    public void publish(ChannelTopic topic, ChatMessage message){
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
