Now you can create a web controller for a simple web application. Using
the **Editor** tab to the right, create a new file
`springboot/src/main/java/com/example/springboot/HelloController.java`
containing the following code:

    package com.example.springboot;

    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    public class HelloController {

            @GetMapping("/")
            public String index() {
                    return "Greetings from Spring Boot!";
            }

    }

The class is flagged as a `@RestController`, meaning it is ready for use
by Spring MVC to handle web requests. `@GetMapping` maps `/` to the
`index()` method. When invoked from a browser or by using curl on the
command line, the method returns pure text. That is because
`@RestController` combines `@Controller` and `@ResponseBody`, two
annotations that results in web requests returning data rather than a
view.

## Create an Application class

The Spring Initializr creates a simple application class for you.
However, in this case, it is too simple. You need to modify the
application class to match the following listing. Update the
`springboot/src/main/java/com/example/springboot/SpringbootApplication.java`
file to contain the following content:

    package com.example.springboot;

    import java.util.Arrays;

    import org.springframework.boot.CommandLineRunner;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.annotation.Bean;

    @SpringBootApplication
    public class SpringbootApplication {

            public static void main(String[] args) {
                    SpringApplication.run(SpringbootApplication.class, args);
            }

            @Bean
            public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
                    return args -> {

                            System.out.println("Let's inspect the beans provided by Spring Boot:");

                            String[] beanNames = ctx.getBeanDefinitionNames();
                            Arrays.sort(beanNames);
                            for (String beanName : beanNames) {
                                    System.out.println(beanName);
                            }

                    };
            }

    }

`@SpringBootApplication` is a convenience annotation that adds all of
the following:

- `@Configuration`: Tags the class as a source of bean definitions for
  the application context.

- `@EnableAutoConfiguration`: Tells Spring Boot to start adding beans
  based on classpath settings, other beans, and various property
  settings. For example, if `spring-webmvc` is on the classpath, this
  annotation flags the application as a web application and activates
  key behaviors, such as setting up a `DispatcherServlet`.

- `@ComponentScan`: Tells Spring to look for other components,
  configurations, and services in the `com/example` package, letting
  it find the controllers.

The `main()` method uses Spring Boot’s `SpringApplication.run()` method
to launch an application. Did you notice that there was not a single
line of XML? There is no `web.xml` file, either. This web application is
100% pure Java and you did not have to deal with configuring any
plumbing or infrastructure.

There is also a `CommandLineRunner` method marked as a `@Bean`, and this
runs on start up. It retrieves all the beans that were created by your
application or that were automatically added by Spring Boot. It sorts
them and prints them out.
