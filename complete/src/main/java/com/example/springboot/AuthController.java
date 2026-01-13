package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	@GetMapping("/admin/auth")
	public UserDetails auth() {
		UserDetails admin = UserDetails.getAdmin();
		admin.validateCreds();
		return admin;
	}

	@GetMapping("/check-session")
	public boolean checkSession() {
		UserDetails user = UserDetails.getUser();
		return user.validateCreds();
	}
}
