package com.shp.fms.chat.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoChatRoomRepository extends MongoRepository<ChatRoomDocument, String> {

	List<ChatRoomDocument> findAllByEmployeeId(String employeeId);
	
	List<ChatRoomDocument> findAllByMemberId(String memberId);
}
