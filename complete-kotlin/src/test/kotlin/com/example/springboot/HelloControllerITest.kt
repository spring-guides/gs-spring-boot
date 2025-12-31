package com.example.springboot

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.test.web.servlet.client.expectBody

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class HelloControllerITest(@Autowired private val client: RestTestClient) {

  @Test
  fun getHello() {
    client.get().uri("/")
      .exchangeSuccessfully()
      .expectBody<String>()
      .isEqualTo("Greetings from Spring Boot!")
  }
}
