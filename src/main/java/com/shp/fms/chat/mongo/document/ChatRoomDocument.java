package com.shp.fms.chat.mongo.document;

import java.time.LocalDateTime;
import java.util.Map;

import javax.persistence.Id;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shp.fms.chat.model.ChatUserStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "chat_room")
public class ChatRoomDocument {
	@Id
	private String id; 		// roomId
	private Map<String, ChatUserStatus> userList; // <userId, 채팅방 사용자 상태 정보>
	
	@LastModifiedDate
	private LocalDateTime updDate;
	
}
