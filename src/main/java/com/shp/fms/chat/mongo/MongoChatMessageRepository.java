package com.shp.fms.chat.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoChatMessageRepository extends MongoRepository<ChatMessageDocument, String> {

//	List<ChatMessageDocument> findAllBy_id(String roomId);
}
