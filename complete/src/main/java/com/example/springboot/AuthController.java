package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class AuthController {

	@GetMapping("/admin/auth")
	public String index() {
        UserDetails admin = new UserDetails("Bee", "Go", "324232352", "054-4453221");
		Logger logger = Logger.getLogger(this.getClass().getName());
		logger.info(admin.firstName + admin.lastName);
		return admin.getName();
		
	}

}
