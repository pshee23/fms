package com.shp.fms.common.exception;

import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private int code;
	private String message;

	public AuthenticationException(String target) {
		super(target);
	}
	
	public AuthenticationException(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
