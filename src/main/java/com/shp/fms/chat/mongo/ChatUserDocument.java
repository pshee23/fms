package com.shp.fms.chat.mongo;

import java.time.LocalDateTime;

import javax.persistence.Id;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "chat_user")
public class ChatUserDocument {
	@Id
	private String _id;
	
	private String roomId;
	
	private String userName;
	
	private String devicekey;
	
	@LastModifiedDate
	private LocalDateTime updDate;
	
}
