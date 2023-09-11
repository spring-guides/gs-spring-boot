You will want to add a test for the endpoint you added, and Spring Test
provides some machinery for that.

You should see the following dependency in your `build.gradle` file:

```
testImplementation 'org.springframework.boot:spring-boot-starter-test'
```

> This is one of [Spring Boot’s
> "`starters`"](http://docs.spring.io/spring-boot/docs/3.1.1/reference/htmlsingle/#using-boot-starter). A starter contains pre-configured bundles of dependencies which allow you to get going quickly without configuring your own dependencies.
> We'll use additional starters in this guide. You can see the complete collection of Spring Boot - supplied starters [in the Spring Boot Starter repository at GitHub](https://github.com/spring-projects/spring-boot/tree/main/spring-boot-project/spring-boot-starters).

## Unit Tests

Now write a simple unit test that mocks the servlet request and response
through your endpoint. Create a file called
`springboot/src/test/java/com/example/springboot/HelloControllerTest.java`
with the following contents:

```java
    package com.example.springboot;

    import static org.hamcrest.Matchers.equalTo;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

    import org.junit.jupiter.api.Test;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.http.MediaType;
    import org.springframework.test.web.servlet.MockMvc;
    import org.springframewoak.test.web.servlet.request.MockMvcRequestBuilders;

    @SpringBootTest
    @AutoConfigureMockMvc
    public class HelloControllerTest {

            @Autowired
            private MockMvc mvc;

            @Test
            public void getHello() throws Exception {
                    mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                                    .andExpect(status().isOk())
                                    .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
            }
    }
```

`MockMvc` comes from Spring Test and lets you, through a set of
convenient builder classes, send HTTP requests into the
`DispatcherServlet` and make assertions about the result. Note the use
of `@AutoConfigureMockMvc` and `@SpringBootTest` to inject a `MockMvc`
instance. Having used `@SpringBootTest`, we are asking for the whole
application context to be created. An alternative would be to ask Spring
Boot to create only the web layers of the context by using
`@WebMvcTest`. In either case, Spring Boot automatically tries to locate
the main application class of your application, but you can override it
or narrow it down if you want to build something different.

## Integration Tests

As well as mocking the HTTP request cycle, you can also use Spring Boot
to write a simple full-stack integration test. For example, instead of
(or as well as) the mock test shown earlier, we could create such an integration test by creating a new file called
`springboot/src/test/java/com/example/springboot/HelloControllerIT.java` with the following contents:

```java
    package com.example.springboot;

    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.boot.test.web.client.TestRestTemplate;
    import org.springframework.http.ResponseEntity;
    import static org.assertj.core.api.Assertions.assertThat;

    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    public class HelloControllerIT {
        @Autowired
        private TestRestTemplate template;

        @Test
        public void getHello() throws Exception {
            ResponseEntity<String> response = template.getForEntity("/", String.class);
            assertThat(response.getBody()).isEqualTo("Greetings from Spring Boot!");
        }
    }
```

The embedded server starts on a random port because of
`webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT`, and the
actual port is configured automatically in the base URL for the
`TestRestTemplate`.
