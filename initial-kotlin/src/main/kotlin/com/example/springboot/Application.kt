package com.example.springboot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
  val ctx = runApplication<Application>(*args)

  println("Let's inspect the beans provided by Spring Boot:")
  val beanNames = ctx.beanDefinitionNames
  beanNames.sorted().forEach { println(it) }
}
