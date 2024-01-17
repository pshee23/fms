//package com.shp.fms.util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BCryptUtil {
//
//	@Autowired
//	private BCryptPasswordEncoder encoder;
//	
//	public String encodePassword(String password) {
//		return encoder.encode(password);
//	}
//	
//	public boolean isPasswordMatch(String password, String dbPassword) {
//		return encoder.matches(password, dbPassword);
//	}
//	
//}
