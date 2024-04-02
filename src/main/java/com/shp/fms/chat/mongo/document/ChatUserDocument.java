package com.shp.fms.chat.mongo.document;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shp.fms.chat.common.type.UserType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "chat_user")
public class ChatUserDocument {
	@Id
	private String id;				// 사용자 ID
	private String name;			// 사용자명
	private UserType type;			// 사용자 타입
	private String deviceToken; 	// 알림을 위한 deviceToken
	private List<String> roomList;
	
	@LastModifiedDate
	private LocalDateTime updDate;
	
}
