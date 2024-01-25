package com.shp.fms.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shp.fms.chat.ChatRoom;
import com.shp.fms.service.MongoDbService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/mongo")
@RestController
@AllArgsConstructor
public class MongoController {
	
//	private final MongoDatabase mongoDatabase;
	
	private final MongoDbService mongoService;

//	@PostMapping("/chat_room")
//	public void registerChatRoom() {
//		MongoCollection<Document> collection = mongoDatabase.getCollection("chat_room");
//		
//		Document document = new Document();
//		document.append("roomId", "2");
//		document.append("name", "3");
//		document.append("lastUpdated", LocalDateTime.now());
//		
//		InsertOneResult result = collection.insertOne(document);
//		log.info("######### registerChatRoom. => {} / {}", result.getInsertedId(), result);
//	}
//	
//	
//	@GetMapping("/chat_room")
//	public void findChatRoom() {
//		MongoCollection<Document> collection = mongoDatabase.getCollection("chat_room");
//		FindIterable<Document> doc = collection.find();
//		Iterator<Document> itr = doc.iterator();
//		
//		while(itr.hasNext()) {
//			log.info("######### findChatRoom. => {}", itr.next());
//		}
//	}
//	
//	@GetMapping("/chat_room/{id}")
//	public void findChatRoom(@PathVariable("id") String id) {
//		MongoCollection<Document> collection = mongoDatabase.getCollection("chat_room");
//		
//		FindIterable<Document> doc = collection.find(eq("_id", new ObjectId(id)));
//		
//		log.info("######### findChatRoom by id. => {} / {}", doc.iterator().next(), doc);
//		
//	}
//	
//	@PutMapping("/chat_room/{id}/{name}")
//	public void changeChatRoomName(@PathVariable("id") String id, @PathVariable("name") String name) {
//		MongoCollection<Document> collection = mongoDatabase.getCollection("chat_room");
//		
//		Bson query = eq("_id", new ObjectId(id));
//		
//		Bson update = Updates.combine(
//				Updates.set("name", name),
//				Updates.currentDate("lastUpdated"));
//		
//		UpdateResult result = collection.updateOne(query, update);
//		log.info("######### changeChatRoomName by id. => {} / {}", result.getModifiedCount(), result);
//	}
//	
//	@DeleteMapping("/chat_room/{id}")
//	public void deleteChatRoomName(@PathVariable("id") String id) {
//		MongoCollection<Document> collection = mongoDatabase.getCollection("chat_room");
//		
//		Bson query = eq("_id", new ObjectId(id));
//				
//		DeleteResult result = collection.deleteOne(query);
//		log.info("######### deleteChatRoomName by id. => {} / {}", result.getDeletedCount(), result);
//	}
	
	@PostMapping("/chat_room/repo")
	public void registerChatRoomRepo() {
		mongoService.registerChatRoom("chat name");
	}
	
	@GetMapping("/chat_room/repo")
	public void findChatRoomRepo() {
		mongoService.findChatRoom();
	}
	
	@GetMapping("/chat_room/repo/{id}")
	public void findChatRoomRepo(@PathVariable("id") String id) {
		mongoService.findChatRoom(id);
	}
	
	@PutMapping("/chat_room/repo/{id}/{name}")
	public void changeChatRoomNameRepo(@PathVariable("id") String id, @PathVariable("name") String name) {
		mongoService.changeChatRoomName(id, name);
	}
	
	@DeleteMapping("/chat_room/repo/{id}")
	public void deleteChatRoomNameRepo(@PathVariable("id") String id) {
		mongoService.deleteChatRoom(id);
	}
	
	@GetMapping("/chat_user/repo")
	public void findAllChatUserRepo(@PathVariable("id") String id) {
		mongoService.findAllChatRoomUser(id);
	}
}
