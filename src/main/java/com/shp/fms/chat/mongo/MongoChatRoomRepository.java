package com.shp.fms.chat.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoChatRoomRepository extends MongoRepository<ChatRoomDocument, String> {

}
