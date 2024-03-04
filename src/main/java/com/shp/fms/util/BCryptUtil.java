package com.shp.fms.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BCryptUtil {

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public String encodePassword(String password) {
		log.info("########## encodePassword. pass={}", password);
		return encoder.encode(password);
	}
	
	public boolean isPasswordMatch(String password, String dbPassword) {
		return encoder.matches(password, dbPassword);
	}
	
}
