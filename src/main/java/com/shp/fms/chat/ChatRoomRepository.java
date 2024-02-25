package com.shp.fms.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import com.shp.fms.service.MongoDbService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {
	
//    // 채팅방(topic)에 발행되는 메시지를 처리할 Listner
    private final RedisMessageListenerContainer redisMessageListener;
    
    // 구독 처리 서비스
    private final RedisMessageSubscriber redisSubscriber;
    
    private final MongoDbService mongoService;
    
    // Redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    
    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        return opsHashChatRoom.values(CHAT_ROOMS);
    }
    
    public List<ChatRoom> findRoomById(String userType, String id) {
    	List<ChatRoom> chatRoomList = new ArrayList<>();
    	if(userType.equals("member")) {
    		chatRoomList = mongoService.findAllChatRoomByMemberId(id);
    	} else if(userType.equals("employee")) {
    		chatRoomList = mongoService.findAllChatRoomByEmployeeId(id);
    	} else {
    		log.error("no type by {}", userType);
    	}
    	
    	return chatRoomList;
    }

    public ChatRoom findRoomById(String id) {
        return opsHashChatRoom.get(CHAT_ROOMS, id);
    }

    /**
     * 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
     */
    public ChatRoom createChatRoom(ChatRoom chatRoom) {
    	String roomId = mongoService.registerChatRoom(chatRoom);

    	if(roomId == null) {
    		return null;
    	}
    	
    	chatRoom.setRoomId(roomId);
        log.info("######## createChatRoom. chatRoom={}", chatRoom);
        
        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
        log.info("######## create result. chatRoom={}", findRoomById(chatRoom.getRoomId()));
        // TODO if error?
        return chatRoom;
    }

    /**
     * 채팅방 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다.
     */
    public void enterChatRoom(String roomId, String chatUser) {
    	log.info("######## enterChatRoom. roomId={}", roomId);
        ChannelTopic topic = topics.get(roomId);
        log.info("######## enterChatRoom. topic={}", topic);
        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
            log.info("######## enterChatRoom. topics={}", topics);
        }
        mongoService.registerChatUser(roomId, chatUser);
    }
    
    public void leaveChatRoom(String roomId, String chatUser) {
    	log.info("######## leaveChatRoom. roomId={}", roomId);
        ChannelTopic topic = topics.get(roomId);
        log.info("######## leaveChatRoom. topic={}", topic);
        if (topic != null) {
           // TODO what to do on background mode?
        } else {
        	log.error("######## leaveChatRoom. no room. roomId={}", roomId);
        }
    }
    
    public void deleteChatRoom(String roomId) {
    	log.info("######## deleteChatRoom. roomId={}", roomId);
        if (opsHashChatRoom.hasKey(CHAT_ROOMS, roomId)) {
        	// TODO how to unsubscribe redis?
        	opsHashChatRoom.delete(CHAT_ROOMS, roomId);
        	mongoService.deleteChatRoom(roomId);
        } else {
        	log.error("######## deleteChatRoom. no room. roomId={}", roomId);
        }
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }
}