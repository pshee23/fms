package com.shp.fms.chat.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shp.fms.chat.mongo.document.ChatUserDocument;

public interface MongoChatUserRepository extends MongoRepository<ChatUserDocument, String> {
	
}
