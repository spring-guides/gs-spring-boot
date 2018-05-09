:spring_boot_version: 2.0.2.RELEASE
:spring-boot: https://github.com/spring-projects/spring-boot
:toc:
:icons: font
:source-highlighter: prettify
:project_id: gs-spring-boot
This guide provides a sampling of how {spring-boot}[Spring Boot] helps you accelerate and facilitate application development. As you read more Spring Getting Started guides, you will see more use cases for Spring Boot.
It is meant to give you a quick taste of Spring Boot. If you want to create your own Spring Boot-based project, visit
http://start.spring.io/[Spring Initializr], fill in your project details, pick your options, and you can download either
a Maven build file, or a bundled up project as a zip file.

== What you'll build
You'll build a simple web application with Spring Boot and add some useful services to it.

== What you'll need

:java_version: 1.8
include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/prereq_editor_jdk_buildtools.adoc[]

include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/how_to_complete_this_guide.adoc[]


include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/hide-show-gradle.adoc[]

include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/hide-show-maven.adoc[]

include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/hide-show-sts.adoc[]

== Learn what you can do with Spring Boot

Spring Boot offers a fast way to build applications. It looks at your classpath and at beans you have configured, makes reasonable assumptions about what you're missing, and adds it. With Spring Boot you can focus more on business features and less on infrastructure.

For example:

- Got Spring MVC? There are several specific beans you almost always need, and Spring Boot adds them automatically. A Spring MVC app also needs a servlet container, so Spring Boot automatically configures embedded Tomcat.
- Got Jetty? If so, you probably do NOT want Tomcat, but instead embedded Jetty. Spring Boot handles that for you.
- Got Thymeleaf? There are a few beans that must always be added to your application context; Spring Boot adds them for you.

These are just a few examples of the automatic configuration Spring Boot provides. At the same time, Spring Boot doesn't get in your way. For example, if Thymeleaf is on your path, Spring Boot adds a `SpringTemplateEngine` to your application context automatically. But if you define your own `SpringTemplateEngine` with your own settings, then Spring Boot won't add one. This leaves you in control with little effort on your part.

NOTE: Spring Boot doesn't generate code or make edits to your files. Instead, when you start up your application, Spring Boot dynamically wires up beans and settings and applies them to your application context.

== Create a simple web application
Now you can create a web controller for a simple web application.

`src/main/java/hello/HelloController.java`
[source,java]
----
include::initial/src/main/java/hello/HelloController.java[]
----

The class is flagged as a `@RestController`, meaning it's ready for use by Spring MVC to handle web requests. `@RequestMapping` maps `/` to the `index()` method. When invoked from a browser or using curl on the command line, the method returns pure text. That's because `@RestController` combines `@Controller` and `@ResponseBody`, two annotations that results in web requests returning data rather than a view.

== Create an Application class
Here you create an `Application` class with the components:

`src/main/java/hello/Application.java`
[source,java]
----
include::complete/src/main/java/hello/Application.java[]
----

include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/spring-boot-application.adoc[]

There is also a `CommandLineRunner` method marked as a `@Bean` and this runs on start up. It retrieves all the beans that were created either by your app or were automatically added thanks to Spring Boot. It sorts them and prints them out.

== Run the application
To run the application, execute:

[subs="attributes"]
----
./gradlew build && java -jar build/libs/{project_id}-0.1.0.jar
----

If you are using Maven, execute:

[subs="attributes"]
----
mvn package && java -jar target/{project_id}-0.1.0.jar
----

You should see some output like this:

....
Let's inspect the beans provided by Spring Boot:
application
beanNameHandlerMapping
defaultServletHandlerMapping
dispatcherServlet
embeddedServletContainerCustomizerBeanPostProcessor
handlerExceptionResolver
helloController
httpRequestHandlerAdapter
messageSource
mvcContentNegotiationManager
mvcConversionService
mvcValidator
org.springframework.boot.autoconfigure.MessageSourceAutoConfiguration
org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration
org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration
org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration$DispatcherServletConfiguration
org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration$EmbeddedTomcat
org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration
org.springframework.boot.context.embedded.properties.ServerProperties
org.springframework.context.annotation.ConfigurationClassPostProcessor.enhancedConfigurationProcessor
org.springframework.context.annotation.ConfigurationClassPostProcessor.importAwareProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration
propertySourcesBinder
propertySourcesPlaceholderConfigurer
requestMappingHandlerAdapter
requestMappingHandlerMapping
resourceHandlerMapping
simpleControllerHandlerAdapter
tomcatEmbeddedServletContainerFactory
viewControllerHandlerMapping
....

You can clearly see **org.springframework.boot.autoconfigure** beans. There is also a `tomcatEmbeddedServletContainerFactory`.

Check out the service.

....
$ curl localhost:8080
Greetings from Spring Boot!
....

== Add Unit Tests

You will want to add a test for the endpoint you added, and Spring Test already provides some machinery for that, and it's easy to include in your project.

Add this to your build file's list of dependencies:

[source,groovy]
----
include::complete/build.gradle[tag=tests]
----

If you are using Maven, add this to your list of dependencies:

[source,xml]
----
include::complete/pom.xml[tag=tests]
----

Now write a simple unit test that mocks the servlet request and response through your endpoint:

`src/test/java/hello/HelloControllerTest.java`
[source,java]
----
include::complete/src/test/java/hello/HelloControllerTest.java[]
----

The `MockMvc` comes from Spring Test and allows you, via a set of convenient builder classes, to send HTTP requests into the `DispatcherServlet` and make assertions about the result. Note the use of the `@AutoConfigureMockMvc` together with `@SpringBootTest` to inject a `MockMvc` instance. Having used `@SpringBootTest` we are asking for the whole application context to be created.  An alternative would be to ask Spring Boot to create only the web layers of the context using the `@WebMvcTest`. Spring Boot automatically tries to locate the main application class of your application in either case, but you can override it, or narrow it down, if you want to build something different.

As well as mocking the HTTP request cycle we can also use Spring Boot to write a very simple full-stack integration test. For example, instead of (or as well as) the mock test above we could do this:

`src/test/java/hello/HelloControllerIT.java`
[source,java]
----
include::complete/src/test/java/hello/HelloControllerIT.java[]
----

The embedded server is started up on a random port by virtue of the `webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT` and the actual port is discovered at runtime with the `@LocalServerPort`.

== Add production-grade services
If you are building a web site for your business, you probably need to add some management services. Spring Boot provides several out of the box with its http://docs.spring.io/spring-boot/docs/{spring_boot_version}/reference/htmlsingle/#production-ready[actuator module], such as health, audits, beans, and more.

Add this to your build file's list of dependencies:

[source,groovy]
----
include::complete/build.gradle[tag=actuator]
----

If you are using Maven, add this to your list of dependencies:

[source,xml]
----
include::complete/pom.xml[tag=actuator]
----

Then restart the app:

[subs="attributes"]
----
./gradlew build && java -jar build/libs/{project_id}-0.1.0.jar
----

If you are using Maven, execute:

[subs="attributes"]
----
mvn package && java -jar target/{project_id}-0.1.0.jar
----

You will see a new set of RESTful end points added to the application. These are management services provided by Spring Boot.

....
2018-03-17 15:42:20.088  ... : Mapped "{[/error],produces=[text/html]}" onto public org.s...
2018-03-17 15:42:20.089  ... : Mapped "{[/error]}" onto public org.springframework.http.R...
2018-03-17 15:42:20.121  ... : Mapped URL path [/webjars/**] onto handler of type [class ...
2018-03-17 15:42:20.121  ... : Mapped URL path [/**] onto handler of type [class org.spri...
2018-03-17 15:42:20.157  ... : Mapped URL path [/**/favicon.ico] onto handler of type [cl...
2018-03-17 15:42:20.488  ... : Mapped "{[/actuator/health],methods=[GET],produces=[application/vnd...
2018-03-17 15:42:20.490  ... : Mapped "{[/actuator/info],methods=[GET],produces=[application/vnd.s...
2018-03-17 15:42:20.491  ... : Mapped "{[/actuator],methods=[GET],produces=[application/vnd.spring...
....

They include: errors, http://localhost:8080/actuator/health[actuator/health], http://localhost:8080/actuator/info[actuator/info], http://localhost:8080/actuator[actuator].

NOTE: There is also a `/actuator/shutdown` endpoint, but it's only visible by default via JMX. To http://docs.spring.io/spring-boot/docs/{spring_boot_version}/reference/htmlsingle/#production-ready-endpoints-enabling-endpoints[enable it as an HTTP endpoint], add
`management.endpoints.shutdown.enabled=true` to your `application.properties` file.

It's easy to check the health of the app.

----
$ curl localhost:8080/actuator/health
{"status":"UP"}
----

You can try to invoke shutdown through curl.

----
$ curl -X POST localhost:8080/actuator/shutdown
{"timestamp":1401820343710,"error":"Method Not Allowed","status":405,"message":"Request method 'POST' not supported"}
----

Because we didn't enable it, the request is blocked by the virtue of not existing.

For more details about each of these REST points and how you can tune their settings with an `application.properties` file, you can read detailed http://docs.spring.io/spring-boot/docs/{spring_boot_version}/reference/htmlsingle/#production-ready-endpoints[docs about the endpoints].

== View Spring Boot's starters
You have seen some of http://docs.spring.io/spring-boot/docs/{spring_boot_version}/reference/htmlsingle/#using-boot-starter[Spring Boot's "starters"]. You can see them all https://github.com/spring-projects/spring-boot/tree/master/spring-boot-project/spring-boot-starters[here in source code].

== JAR support and Groovy support
The last example showed how Spring Boot makes it easy to wire beans you may not be aware that you need. And it showed how to turn on convenient management services.

But Spring Boot does yet more. It supports not only traditional WAR file deployments, but also makes it easy to put together executable JARs thanks to Spring Boot's loader module. The various guides demonstrate this dual support through the `spring-boot-gradle-plugin` and `spring-boot-maven-plugin`.

On top of that, Spring Boot also has Groovy support, allowing you to build Spring MVC web apps with as little as a single file.

Create a new file called **app.groovy** and put the following code in it:

[source,groovy]
----
@RestController
class ThisWillActuallyRun {

    @RequestMapping("/")
    String home() {
        return "Hello World!"
    }

}
----

NOTE: It doesn't matter where the file is. You can even fit an application that small inside a https://twitter.com/rob_winch/status/364871658483351552[single tweet]!

Next, http://docs.spring.io/spring-boot/docs/{spring_boot_version}/reference/htmlsingle/#getting-started-installing-the-cli[install Spring Boot's CLI].

Run it as follows:

----
$ spring run app.groovy
----

NOTE: This assumes you shut down the previous application, to avoid a port collision.

From a different terminal window:
----
$ curl localhost:8080
Hello World!
----

Spring Boot does this by dynamically adding key annotations to your code and using http://groovy.codehaus.org/Grape[Groovy Grape] to pull down libraries needed to make the app run.

== Summary
Congratulations! You built a simple web application with Spring Boot and learned how it can ramp up your development pace. You also turned on some handy production services.
This is only a small sampling of what Spring Boot can do. Checkout http://docs.spring.io/spring-boot/docs/{spring_boot_version}/reference/htmlsingle[Spring Boot's online docs]
if you want to dig deeper.

== See Also

The following guides may also be helpful:

* https://spring.io/guides/gs/securing-web/[Securing a Web Application]
* https://spring.io/guides/gs/serving-web-content/[Serving Web Content with Spring MVC]

include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/footer.adoc[]
