//package com.shp.fms.common.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoDatabase;
//
//@Configuration
//public class MongoDbConfig {
//
//	@Value("${spring.mongo.url}")
//	private String mongoUrl;
//	 
//	@Bean
//	MongoDatabase mongoDatabase() {
//		MongoClient mongoClient = MongoClients.create(mongoUrl);
//		return database;
//	}
//}
