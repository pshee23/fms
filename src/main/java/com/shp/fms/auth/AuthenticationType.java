package com.shp.fms.auth;

import lombok.Getter;

public enum AuthenticationType {
	BAD_CREDENTIAL("BadCredentialsException", "wrong password"),
    USER_NOT_FOUND("UsernameNotFoundException", "no account"),
    ACCOUNT_EXPIRED("AccountExpiredException", "account expired"),
    CREDENTIALS_EXPIRED("CredentialsExpiredException", "password expired"),
    DISABLED("DisabledException", "account disabled"),
    LOCKED("LockedException", "account locked"),
    NONE("NoneException", "unknown error");

	@Getter
    private final String name;

	@Getter
    private final String message;

    AuthenticationType(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public static String getExceptionMessage(String exceptionName) {
    	for(AuthenticationType type : AuthenticationType.values()) {
    		if(type.getName().equals(exceptionName)) {
    			return type.getMessage();
    		}
    	}
    	return AuthenticationType.NONE.getName();
    }
    
}
