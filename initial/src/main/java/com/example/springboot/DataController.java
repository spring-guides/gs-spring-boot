package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController {

    public DataController() {}

	@GetMapping("")
	public String index() {
		return "Let's look at data";
	}

    @GetMapping("/{userId}")
    public String getData(@PathVariable Long userId){
        return "This is user " + userId;
    }


    @GetMapping("/{userId}/age")
    public String getAge(@PathVariable Long userId){
        return "This is user " + userId + ", they are old!";
    }
}
