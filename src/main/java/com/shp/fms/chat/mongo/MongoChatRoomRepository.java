package com.shp.fms.chat.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shp.fms.chat.mongo.document.ChatRoomDocument;

public interface MongoChatRoomRepository extends MongoRepository<ChatRoomDocument, String> {

	List<ChatRoomDocument> findAllById(String roomId);
}
