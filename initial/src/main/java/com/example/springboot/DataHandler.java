package com.example.springboot;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Component
public class DataHandler {

	public ServerResponse index(ServerRequest request) {
		/**/
		return ServerResponse.ok().build();
	}

	public ServerResponse getData(ServerRequest request) {
		/**/
		return ServerResponse.ok().build();
	}

	public ServerResponse getAge(ServerRequest request) {
		/**/
		return ServerResponse.ok().build();
	}

}