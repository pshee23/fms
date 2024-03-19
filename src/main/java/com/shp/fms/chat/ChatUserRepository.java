package com.shp.fms.chat;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.shp.fms.chat.ChatMessage.MessageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ChatUserRepository {
	
	// TODO object lock
    // Redis
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatUser> opsHashChatUser;
    
    @PostConstruct
    private void init() {
        opsHashChatUser = redisTemplate.opsForHash();
    }
    
    public List<ChatUser> getChatUserList(String roomId, String employeeId, String memberId) {
    	List<ChatUser> resultList = new ArrayList<>();
    	resultList.add(opsHashChatUser.get(roomId, employeeId));
    	resultList.add(opsHashChatUser.get(roomId, memberId));
    	return resultList;
    }
    
    public void updateUserState(String roomId, String sender, MessageType type) {
    	log.info("######## updateUserState. roomId={}, sender={}, type={}", roomId, sender, type);
    	ChatUser chatUser = opsHashChatUser.get(roomId, sender);
    	chatUser.setStatus(type);
    	opsHashChatUser.put(roomId, sender, chatUser);
    }

    public boolean isUserBackground(String roomId, String userId) {
    	log.info("######## isUserBackground. userId={}", userId);
    	ChatUser chatUser = opsHashChatUser.get(roomId, userId);
    	log.info("######## isUserBackground. chatUser={}", chatUser);
    	if(chatUser == null || chatUser.getStatus() == MessageType.LEAVE) {
    		return true;
    	}
    	return false;
    }
}