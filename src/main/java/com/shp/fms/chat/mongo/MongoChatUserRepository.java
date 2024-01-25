package com.shp.fms.chat.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoChatUserRepository extends MongoRepository<ChatUserDocument, String> {

	List<ChatUserDocument> findAllByRoomId(String roomId);
	
	void deleteByRoomIdAndUserName(String roomId, String userName);
}
