package com.shp.fms.chat.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shp.fms.chat.mongo.document.ChatMessageDocument;

public interface MongoChatMessageRepository extends MongoRepository<ChatMessageDocument, String> {

	List<ChatMessageDocument> findAllByRoomId(String roomId);
}
