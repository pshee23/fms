package com.shp.fms.auth;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

@Repository
public class AuthRepository {
	private Map<String, String> userJwtTokenMap;
	
	@PostConstruct
	public void init() {
		this.userJwtTokenMap = new HashMap<>();
	}
	
	public void addToken(String username, String token) {
		userJwtTokenMap.put(username, token);
	}
	
	public boolean removeToken(String username, String token) {
		return userJwtTokenMap.remove(username, token);
	}
	
	public boolean isTokenValid(String username, String token) {
		String savedToken = userJwtTokenMap.get(username);
		if(savedToken == null || !savedToken.equals(token)) {
			return false;
		} else {
			return true;
		}
	}
}
