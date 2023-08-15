package com.shp.fms.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shp.fms.model.request.MemberRequestBody;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthController {
	
	private final AuthService service;
	
	@GetMapping({"","/"})
	public @ResponseBody String index() {
		return "index";
	}
	
	@GetMapping({"/user"})
	public @ResponseBody String user() {
		return "user";
	}
	
	@GetMapping({"/admin"})
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping({"/manager"})
	public @ResponseBody String manager() {
		return "manager";
	}
	
	@GetMapping({"/loginForm"})
	public @ResponseBody String login() {
		return "loginForm";
	}
	
	@GetMapping({"/joinForm"})
	public @ResponseBody String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("/join")
	public String join(@RequestBody MemberRequestBody request) {
		System.out.println(request);
		
		service.signUp(request);
		return "redirect:/loginForm";
	}
	
	@GetMapping("/ok")
	public ResponseEntity<String> ok() {
		return ResponseEntity.ok("OK");
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인 정보";
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGE') or hasRole('ROLE_ADMIN')")
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "데이터 정보";
	}
}
