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
	
    // Redis
	private static final String CHAT_USERS = "CHAT_USER";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatUser> opsHashChatUser;
    
    @PostConstruct
    private void init() {
        opsHashChatUser = redisTemplate.opsForHash();
    }
    
    public List<ChatUser> getChatUserList(String employeeId, String memberId) {
    	List<ChatUser> resultList = new ArrayList<>();
    	resultList.add(opsHashChatUser.get(CHAT_USERS, employeeId));
    	resultList.add(opsHashChatUser.get(CHAT_USERS, memberId));
    	return resultList;
    }
    
    public void updateUserState(ChatUser chatUser) {
    	log.info("######## updateUserState. body={}", chatUser);
    	opsHashChatUser.put(CHAT_USERS, chatUser.getId(), chatUser);
    }

    public boolean isUserBackground(String id) {
    	log.info("######## isUserBackground. id={}", id);
    	ChatUser chatUser = opsHashChatUser.get(CHAT_USERS, id);
    	log.info("######## isUserBackground. chatUser={}", chatUser);
    	if(chatUser == null || chatUser.getStatus() == MessageType.LEAVE) {
    		return true;
    	}
    	return false;
    }
    
}