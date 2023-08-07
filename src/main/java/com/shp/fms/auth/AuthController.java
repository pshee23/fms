package com.shp.fms.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RequestMapping("/auth")
@RestController
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/login")
    public String login(@RequestBody LoginRequestBody requestBody) {
        return "login";
    }
	
	@PostMapping("/sign-up")
    public String signUp(@RequestBody SignUpRequestBody requestBody) {
		authService.signUp(requestBody);
        return "signUp";
    }
}
