package com.example.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RequestPredicate;
import static org.springframework.web.servlet.function.RequestPredicates.accept;
import org.springframework.web.servlet.function.RouterFunction;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class DataRoutingConfig {
    private static final RequestPredicate ACCEPT_JSON = accept(MediaType.APPLICATION_JSON);

	@Bean
	public RouterFunction<ServerResponse> routerFunction(DataHandler dataHandler) {
		return route()
				.GET("/", ACCEPT_JSON,dataHandler::index)
				.GET("/{userId}", ACCEPT_JSON,dataHandler::getData)
				.GET("/{userId}/age", ACCEPT_JSON,dataHandler::getAge)
				.build();
	}
}