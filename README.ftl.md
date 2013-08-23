<#assign project_id="gs-spring-boot">
This guide provides a sampling of how [Spring Boot][spring-boot] helps you accelerate and facilitate application development. As you read more Spring Getting Started guides, you will see more use cases for Spring Boot.

What you'll build
-----------------
You'll build a simple web application with Spring Boot and add some useful services to it.

What you'll need
----------------

 - About 15 minutes
 - <@prereq_editor_jdk_buildtools/>

## <@how_to_complete_this_guide jump_ahead='Learn what you can do with Spring Boot'/>


<a name="scratch"></a>
Set up the project
------------------

<@build_system_intro/>

<@create_directory_structure_hello/>


<@create_both_builds/>

Learn what you can do with Spring Boot
--------------------------------------

Spring Boot offers a fast way to build applications. It looks at your classpath and at beans you have configured, makes reasonable assumptions about what you're missing, and adds it. With Spring Boot you can focus more on business features and less on infrastructure.

For example:
- Got Spring MVC? There are several specific beans you almost always need, and Spring Boot adds them automatically. A Spring MVC app also needs a servlet container, so Spring Boot automatically configures embedded Tomcat.
- Got Jetty? If so, you probably do NOT want Tomcat, but instead embedded Jetty. Spring Boot handles that for you.
- Got Thymeleaf? There are a few beans that must always be added to your application context; Spring Boot adds them for you.
- Doing multipart file uploads? [MultipartConfigElement](http://docs.oracle.com/javaee/6/api/javax/servlet/MultipartConfigElement.html) is part of the servlet 3.0 spec and lets you define upload parameters in pure Java. With Spring Boot, you don't have to plug a MultipartConfigElement into your servlet. Just define one in your application context and Spring Boot will plug it into Spring MVC's battle-tested `DispatcherServlet`.

These are just a few examples of the automatic configuration Spring Boot provides. At the same time, Spring Boot doesn't get in your way. For example, if Thymeleaf is on your path, Spring Boot adds a `SpringTemplateEngine` to your application context automatically. But if you define your own `SpringTemplateEngine` with your own settings, then Spring Boot won't add one. This leaves you in control with little effort on your part.

> **Note:** Spring Boot doesn't generate code or make edits to your files. Instead, when you start up your application, Spring Boot dynamically wires up beans and settings and applies them to your application context.

Create a simple web application
---------------------------------
Now you can create a web controller for a simple web application.

    <@snippet path="src/main/java/hello/HelloController.java" prefix="initial"/>
    
The class is flagged as a `@Controller`, meaning it's ready for use by Spring MVC to handle web requests. `@RequestMapping` maps `/` to the `index()` method. When invoked from a browser or using curl on the command line, the method returns pure text thanks to the `@ResponseBody` annotation.

Create an Application class
---------------------------
Here you create an `Application` class with the components:

    <@snippet path="src/main/java/hello/Application.java" prefix="initial"/>
    
- `@Configuration` tags the class as a source of bean definitions for the application context.
- `@EnableAutoConfiguration` tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
- `@EnableWebMvc` signals Spring MVC that this application is a web application and to activate key behaviors such as setting up a `DispatcherServlet`.
- `@ComponentScanning` tells Spring to look for other components, configurations, and services in the the `hello` package, allowing it to find the `HelloController`.

The `main()` method uses Spring Boot's `SpringApplication.run()` method to launch an application. Did you notice that there wasn't a single line of XML? No **web.xml** file either. This web application is 100% pure Java and you didn't have to deal with configuring any plumbing or infrastructure.

The `run()` method returns an `ApplicationContext` and this application then retrieves all the beans that were created either by your app or were automatically added thanks to Spring Boot. It sorts them and prints them out.

Run the application
-------------------
To run the application, execute:

```sh
$ ./gradlew build && java -jar build/libs/${project_id}-0.1.0.jar
```

If you are using Maven, execute:

```sh
$ mvn package && java -jar target/${project_id}-0.1.0.jar
```

You should see some output like this:

```sh
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
```

You can clearly see **org.springframework.boot.autoconfigure** beans. There is also a `tomcatEmbeddedServletContainerFactory`.

Check out the service.

```sh
$ curl localhost:8080
Greetings from Spring Boot!
```

Switch from Tomcat to Jetty
---------------------------
What if you prefer Jetty over Tomcat? Jetty and Tomcat are both compliant servlet containers, so it should be easy to switch. With Spring Boot, it is!

Add this to your `build.gradle` list of dependencies:

```groovy
    compile("org.springframework.boot:spring-boot-starter-jetty:0.5.0.BUILD-SNAPSHOT")
```

If you are using Maven, add this to your list of dependencies:

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jetty</artifactId>
        </dependency>
```

Add multipart upload support
-------------------------------
Suppose you want to add file upload support. To do that, update your configuration by adding a `MultipartConfigElement` to the application context.

    <@snippet path="src/main/java/hello/Application.java" prefix="complete"/>
    
> **Note:** A production version of `MultipartConfigElement` would not be empty; it would specify target upload path, file size upload limits, and so forth.

Re-run the application
----------------------

Run the app again:

```sh
$ ./gradlew build && java -jar build/libs/${project_id}-0.1.0.jar
```

If you are using Maven, execute:

```sh
$ mvn package && java -jar target/${project_id}-0.1.0.jar
```

Now check out the output:

```sh
Let's inspect the beans provided by Spring Boot:
application
beanNameHandlerMapping
defaultServletHandlerMapping
dispatcherServlet
embeddedServletContainerCustomizerBeanPostProcessor
handlerExceptionResolver
helloController
httpRequestHandlerAdapter
jettyEmbeddedServletContainerFactory
messageSource
multipartConfigElement
multipartResolver
mvcContentNegotiationManager
mvcConversionService
mvcValidator
org.springframework.boot.autoconfigure.MessageSourceAutoConfiguration
org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration
org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration
org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration$DispatcherServletConfiguration
org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration$EmbeddedJetty
org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration
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
viewControllerHandlerMapping
```

Little changes from the previous output, except there is no longer a `tomcatEmbeddedServletContainerFactory`. Instead, there is a new `jettyEmbeddedServletContainer`. 

There is also the `multipartConfigElement` you added. But along with it came a `multipartResolver` [courtesy of Spring Boot](https://github.com/SpringSource/spring-boot/blob/master/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/MultipartAutoConfiguration.java), a bean recommended to support file uploads with Spring MVC.

Otherwise, everything is the same, as it should be. Most beans listed above provide Spring MVC's production-grade features. Simply swapping one part, the servlet container, and adding upload support shouldn't cause a system-wide ripple.

Add consumer-grade services
------------------------------
If you are building a web site for your business, you probably need to add some management services. Spring Boot provides several out of the box with its [actuator module][spring-boot-actuator], such as health, audits, beans, and more.

Add this to your build file's list of dependencies:

```groovy
    compile("org.springframework.boot:spring-boot-starter-actuator:0.5.0.BUILD-SNAPSHOT")
```

If you are using Maven, add this to your list of dependencies:

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
```

Then restart the app:

```sh
$ ./gradlew build && java -jar build/libs/${project_id}-0.1.0.jar
```

If you are using Maven, execute:

```sh
$ mvn package && java -jar target/${project_id}-0.1.0.jar
```

You will see a new set of RESTful end points added to the application. These are management services provided by Spring Boot.

```sh
2013-08-01 08:03:42.592  INFO 43851 ... Mapped "{[/error],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}" onto public java.util.Map<java.lang.String, java.lang.Object> org.springframework.boot.ops.web.BasicErrorController.error(javax.servlet.http.HttpServletRequest)
2013-08-01 08:03:42.592  INFO 43851 ... Mapped "{[/error],methods=[],params=[],headers=[],consumes=[],produces=[text/html],custom=[]}" onto public org.springframework.web.servlet.ModelAndView org.springframework.boot.ops.web.BasicErrorController.errorHtml(javax.servlet.http.HttpServletRequest)
2013-08-01 08:03:42.844  INFO 43851 ... Mapped URL path [/env] onto handler of type [class org.springframework.boot.ops.endpoint.EnvironmentEndpoint]
2013-08-01 08:03:42.844  INFO 43851 ... Mapped URL path [/health] onto handler of type [class org.springframework.boot.ops.endpoint.HealthEndpoint]
2013-08-01 08:03:42.844  INFO 43851 ... Mapped URL path [/beans] onto handler of type [class org.springframework.boot.ops.endpoint.BeansEndpoint]
2013-08-01 08:03:42.844  INFO 43851 ... Mapped URL path [/info] onto handler of type [class org.springframework.boot.ops.endpoint.InfoEndpoint]
2013-08-01 08:03:42.845  INFO 43851 ... Mapped URL path [/metrics] onto handler of type [class org.springframework.boot.ops.endpoint.MetricsEndpoint]
2013-08-01 08:03:42.845  INFO 43851 ... Mapped URL path [/trace] onto handler of type [class org.springframework.boot.ops.endpoint.TraceEndpoint]
2013-08-01 08:03:42.845  INFO 43851 ... Mapped URL path [/dump] onto handler of type [class org.springframework.boot.ops.endpoint.DumpEndpoint]
2013-08-01 08:03:42.845  INFO 43851 ... Mapped URL path [/shutdown] onto handler of type [class org.springframework.boot.ops.endpoint.ShutdownEndpoint]
```

They include: errors, [environment](http://localhost:8080/env), [health](http://localhost:8080/health), [beans](http://localhost:8080/beans), [info](http://localhost:8080/info), [metrics](http://localhost:8080/metrics), [trace](http://localhost:8080/trace), [dump](http://localhost:8080/dump), and shutdown.

It's easy to check the health of the app.

```sh
$ curl localhost:8080/health
ok
```

You can invoke shutdown through curl.

```sh
$ curl -X POST localhost:8080/shutdown
```

The response shows that shutdown through REST is currently disabled by default:
```sh
{"message":"Shutdown not enabled, sorry."}
```

Whew! You probably don't want that until you are ready to turn on proper security settings, if at all.

For more details about each of these REST points and how you can tune their settings with an `application.properties` file, check out the [Spring Boot][spring-boot] project.

View Spring Boot's starters
----------------------
You have seen some of Spring Boot's **starters**. Here is a complete list:
- spring-boot-starter-actuator
- spring-boot-starter-batch
- spring-boot-starter-data-jpa
- spring-boot-starter-integration
- spring-boot-starter-jetty
- spring-boot-starter-logging
- spring-boot-starter-parent
- spring-boot-starter-security
- spring-boot-starter-tomcat
- spring-boot-starter-web


JAR support and Groovy support
------------------------------
The last example showed how Spring Boot makes it easy to wire beans you may not be aware that you need. And it showed how to turn on convenient management services.

But Spring Boot does yet more. It supports not only traditional WAR file deployments, but also makes it easy to put together executable JARs thanks to Spring Boot's loader module. The various guides demonstrate this dual support through the `spring-boot-gradle-plugin`.

On top of that, Spring Boot also has Groovy support, allowing you to build web apps with as little as a single file.

Create a new file called **app.groovy** and put the following code in it:

```groovy
@Controller
class ThisWillActuallyRun {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!"
    }

}
```

> **Note:** It doesn't matter where the file is. You can even fit an application that small inside a [single tweet](https://twitter.com/rob_winch/status/364871658483351552)!

Next, [install Spring Boot's CLI](https://github.com/SpringSource/spring-boot#installing-the-cli).

Run it as follows:

```sh
$ spring run app.groovy
```

> **Note:** This assumes you shut down the previous application, to avoid a port collision.

From a different terminal window:
```sh
$ curl localhost:8080
Hello World!
```

Spring Boot dynamically adds key annotations to your code and leverages [Groovy Grapes](http://groovy.codehaus.org/Grape) to pull down needed libraries to make the app run.

Summary
----------------
Congratulations! You built a simple web application with Spring Boot and learned how it can ramp up your development pace. You also turned on some handy production services.

[spring-boot]: https://github.com/SpringSource/spring-boot
[spring-boot-actuator]: https://github.com/SpringSource/spring-boot/blob/master/spring-boot-actuator/README.md
