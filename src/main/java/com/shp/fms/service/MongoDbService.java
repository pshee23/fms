package com.shp.fms.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.shp.fms.chat.ChatRoomRepository;
import com.shp.fms.chat.common.type.UserType;
import com.shp.fms.chat.model.ChatMessage;
import com.shp.fms.chat.model.ChatMessage.MessageType;
import com.shp.fms.chat.model.ChatRoom;
import com.shp.fms.chat.model.ChatUserStatus;
import com.shp.fms.chat.model.request.CreateChatRoomRequest;
import com.shp.fms.chat.mongo.MongoChatMessageRepository;
import com.shp.fms.chat.mongo.MongoChatRoomRepository;
import com.shp.fms.chat.mongo.MongoChatUserRepository;
import com.shp.fms.chat.mongo.document.ChatMessageDocument;
import com.shp.fms.chat.mongo.document.ChatRoomDocument;
import com.shp.fms.chat.mongo.document.ChatUserDocument;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MongoDbService {
	
	private final MongoChatRoomRepository chatRoomRepository;
	
	private final MongoChatUserRepository chatUserRepository;
	
	private final MongoChatMessageRepository chatMessageRepository;
	
	private final ChatRoomRepository chatRepository;

	public String registerChatRoom(CreateChatRoomRequest req) {
		Map<String, ChatUserStatus> userList = new HashMap<>();
		userList.put(req.getEmployeeId(), new ChatUserStatus());
		userList.put(req.getMemberId(), new ChatUserStatus());
		
		ChatRoomDocument document = ChatRoomDocument.builder()
				.userList(userList)
				.updDate(LocalDateTime.now())
				.build();
		
		try {
			ChatRoomDocument result = chatRoomRepository.insert(document);
			log.info("registerChatRoom success. => {}", result);
			return result.getId();
		} catch(OptimisticLockingFailureException ex) {
			log.error("registerChatRoom fail. doc={}", document, ex);
			return null;
		}
	}
	
	public ChatRoomDocument findChatRoomByRoomId(String roomId) {
		return chatRoomRepository.findById(roomId).get();
	}
	
	public ChatUserDocument findChatUserDocByUserId(String userId) {
		Optional<ChatUserDocument> opList = chatUserRepository.findById(userId);
		if(opList.isEmpty()) {
			return null;
		}
		return opList.get();
	}
	
	public List<ChatRoom> findAllChatRoomByMemberId(String memberId) {
		Optional<ChatUserDocument> document = chatUserRepository.findById(memberId);
		if(document.isEmpty()){
			return new ArrayList<>();
		}
		List<ChatRoomDocument> documentList = new ArrayList<>();
		for(String roomId : document.get().getRoomList()) {
			Optional<ChatRoomDocument> docRoom = chatRoomRepository.findById(roomId);
			if(docRoom.isEmpty()) continue;
			documentList.add(docRoom.get());
		}
		log.info("######### findAllChatRoomByMemberId. => {}", documentList);
		return mapChatRoom(documentList);
	}
	
	private List<ChatRoom> mapChatRoom(List<ChatRoomDocument> documentList) {
		List<ChatRoom> roomList = new ArrayList<>();
		for(ChatRoomDocument document : documentList) {
			ChatRoom chatRoom = new ChatRoom();
			chatRoom.setRoomId(document.getId());
			
			roomList.add(chatRoom);
		}
		return roomList;
	}
	
	public void registerChatUser(String roomId, String userId) {
		// XXX 쿼리로 잘 활용할 방안이 있을까?
		Optional<ChatUserDocument> userOp = chatUserRepository.findById(userId);
		if(userOp.isPresent()) {
			ChatUserDocument doc = userOp.get();
			List<String> roomList = doc.getRoomList();
			roomList.add(roomId);
			doc.setRoomList(roomList);
			ChatUserDocument result = chatUserRepository.save(doc);
			log.info("updateChatUser. => {}", result);
		} else {
			List<String> roomList = new ArrayList<>();
			roomList.add(roomId);
			ChatUserDocument document = ChatUserDocument.builder()
					.id(userId)
					.name("임시 이름. " + userId) // TODO id로 이름 검색. 회원 직원 테이블 통합
					.type(UserType.NONE) // 위와 같음
					.roomList(roomList)
					.build();
			ChatUserDocument result = chatUserRepository.insert(document);
			log.info("registerChatUser. => {}", result);	
		}
	}
	
	public void updateChatUserStatus(String roomId, String userId, MessageType type) {
		Optional<ChatRoomDocument> document = chatRoomRepository.findById(roomId);
		if(document.isEmpty()) {
			log.warn("Chat room doesn't exist. roomId={}", roomId);
			return;
		}
		
		// TODO map value 변경 방법
		ChatRoomDocument doc = document.get();
		log.info("##### before set. doc={}", doc);
		Map<String, ChatUserStatus> statusMap = doc.getUserList();
		ChatUserStatus status = statusMap.get(roomId);
		status.updateStatus(type);
		chatRoomRepository.save(doc);
		log.info("##### after set. doc={}", doc);
	}
	
	public void deleteChatUser(String userId) {
		chatUserRepository.deleteById(userId);
		log.info("######### deleteChatUser by userId. => {}", userId);
	}
	
	public void registerChatMessage(String roomId, String userName, String message) {
		ChatMessageDocument document = ChatMessageDocument.builder().roomId(roomId).userName(userName).message(message).build();
		ChatMessageDocument result = chatMessageRepository.insert(document);
		log.info("######### registerChatMessage. => {}", result);
	}
	
	public List<ChatMessage> getChatMessages(String roomId) {
		List<ChatMessageDocument> resultList = chatMessageRepository.findAllByRoomId(roomId);
		log.info("######### getChatMessages. => {}", resultList);
		List<ChatMessage> chatList = new ArrayList<>();
		for(ChatMessageDocument doc : resultList) {
			ChatMessage message = new ChatMessage();
			message.setRoomId(roomId);
			message.setSender(doc.getUserName());
			message.setContent(doc.getMessage());
			chatList.add(message);
		}
		return chatList;
	}
	
    public List<ChatRoom> getRoomListByUserId(String userId) {
    	ChatUserDocument chatDoc = findChatUserDocByUserId(userId);
    	List<String> roomList = chatDoc.getRoomList();
    	List<ChatRoom> chatRoomList = new ArrayList<>();
    	for(String roomId : roomList) {
    		ChatRoomDocument roomDoc = findChatRoomByRoomId(roomId);
    		if(roomDoc == null) {
    			log.warn("can not find room. roomId={}", roomId);
    			continue;
    		}
    		String toId = roomDoc.getUserList().entrySet()
    				.stream()
    				.filter(entry -> !entry.getKey().equals(userId))
    				.findFirst().get().getKey();
    		if(toId == null) {
    			log.warn("can not find other chat user. roomId={}", roomId);
    			continue;
    		}
    		ChatUserDocument toDoc = findChatUserDocByUserId(toId);
    		if(toDoc == null) {
    			log.warn("can not find other chat user info. userId={}", toId);
    			continue;
    		}
    		
    		ChatRoom chatRoom = new ChatRoom();
    		chatRoom.setRoomId(roomId);
    		chatRoom.setTitle(toDoc.getName()); // TODO 제목 설정?
    		chatRoom.setFromId(userId);
    		chatRoom.setFromName(chatDoc.getName());
    		chatRoom.setToId(toId);
    		chatRoom.setToName(toDoc.getName());
    		
    		chatRoomList.add(chatRoom);
    	}
    	
    	return chatRoomList;
    }
    
    /**
     * 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
     */
    public String makeChatRoom(CreateChatRoomRequest createRequest) {
    	String roomId = registerChatRoom(createRequest);

    	if(roomId == null) {
    		return null;
    	}
    	
    	chatRepository.processTopic(roomId);
    	
        registerChatUser(roomId, createRequest.getEmployeeId());
        registerChatUser(roomId, createRequest.getMemberId());
        return roomId;
    }

    /**
     * 채팅방 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다.
     */
    public void enterChatRoom(String roomId, String userId) {
    	log.info("enter chat room. roomId={}, userId={}", roomId, userId);
    	
        chatRepository.processTopic(roomId);
        
        updateChatUserStatus(roomId, userId, MessageType.JOIN);
    }
    
    public void leaveChatRoom(String roomId, String userId) {
    	log.info("leave chat room. roomId={}", roomId);
        ChannelTopic topic = chatRepository.getTopic(roomId);
        if (topic != null) {
        	updateChatUserStatus(roomId, userId, MessageType.LEAVE);
        } else {
        	log.warn("leave chat room fail. No room to leave. roomId={}", roomId);
        }
    }
    
    public void deleteChatRoom(String roomId, String userId) {
    	log.info("delete chat room. roomId={}", roomId);
        if (chatRoomRepository.existsById(roomId)) {
        	chatRoomRepository.deleteById(roomId);
        	
        	// TODO chatUserRepository에서 list에서 방 정보 삭제
        	ChatUserDocument chatDocument = chatUserRepository.findById(userId).get();
        	log.info("#### before delete room. doc={}", chatDocument);
        	chatDocument.getRoomList().stream().filter(v -> !v.equals(roomId));
        	chatUserRepository.save(chatDocument);
        	log.info("#### after delete room. doc={}", chatUserRepository.findById(userId));
    		log.info("######### deleteChatRoomName by id. => {}", roomId);
        } else {
        	log.error("######## deleteChatRoom. no room. roomId={}", roomId);
        }
    }
}
