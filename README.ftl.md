<#assign project_id="gs-spring-boot">

What you'll build
-----------------

This guide provides an introduction to [Spring Boot][spring-boot] by building a simple web application. This doesn't demonstrate all of its features but will help you get started with the concepts Spring Boot has to offer.

What you'll need
----------------

 - About 15 minutes
 - <@prereq_editor_jdk_buildtools/>


## <@how_to_complete_this_guide jump_ahead="Warming up with Spring Boot"/>


<a name="scratch"></a>
Set up the project
------------------

<@build_system_intro/>

<@create_directory_structure_hello/>

### Create a Maven POM

    <@snippet path="pom.xml" prefix="initial"/>

Warming up with Spring Boot
---------------------------

What does Spring Boot provide? At the core, it offers a much faster way to build applications because it make reasonable assumptions such as looking at your classpath and other beans you have configured to see what you're missing.

For example:
- Got Spring MVC? There are a handful of needed beans people almost always use in that situation. Spring Boot adds them automatically. But why stop there? A Spring MVC app needs a servlet container so Spring Boot automatically configures embedded Tomcat.
- Got Jetty? You probably do NOT want Tomcat, but instead embedded Jetty. Don't lift a finger; Spring Boot handles it for you.
- Got Thymeleaf? There are a few beans that must always be added to your application context. Why should you have to deal with that? Let Spring Boot handle it for you.
- Doing multipart file uploads? The [MultipartConfigElement](http://docs.oracle.com/javaee/6/api/javax/servlet/MultipartConfigElement.html) is part of the servlet 3.0 spec and let's you define upload parameters in pure Java. Why should you have to worry about plugging that into your servlet? Define one in your application context and Spring Boot will snatch it up and plug it into Spring MVC's battle tested `DispatcherServlet`.

It doesn't stop there. These are just a few examples of the automatic configuration provided by Spring Boot. But it doesn't get in your way. For example, Spring Boot may make assumptions and add a `SpringTemplateEngine` for your Thymeleaf-based application, unless you've already defined one. At that point, Spring Boot automatically steps aside and lets you take control.

Creating a simple web application
---------------------------------
You already have the base build file at the top. Next step is to create a web controller for a simple web application.

    <@snippet path="src/main/java/hello/HelloController.java" prefix="initial"/>
    
The class is flagged as a `@Controller` meaning it's ready for use by Spring MVC to handle web requests. `@RequestMapping` maps `/` to the `index()` method. When invoked from a browser or using curl on the command line, it returns pure text thanks to the `@ResponseBody` annotation.

To make it executable, create an `Application` class:

    <@snippet path="src/main/java/hello/Application.java" prefix="initial"/>
    
- `@Configuration` tags the class as a source of bean definitions for the application context.
- `@EnableAutoConfiguration` tells Spring Boot to get going and start adding beans based on classpath settings, other beans, and various property settings.
- `@EnableWebMvc` signals Spring MVC that this application is a web application and to activate key behaviors such as setting up a `DispatcherServlet`.
- `@ComponentScanning` tells Spring to look for other components, configurations, and services in the the `hello` package, allowing it to find the `HelloController`.

The `main()` method uses Spring Boot's `SpringApplication.run()` method to launch an application. Did you notice that there wasn't a single line of XML? No **web.xml** file either. This web application is 100% pure Java and you didn't have to deal with configuring any plumbing or infrastructure.

The `run()` method returns an `ApplicationContext` and this application then retrieves all the beans that were created either by your app or were automatically added thanks to Spring Boot. It sorts them and prints them out.

To run it, execute:

```sh
$ mvn package spring-boot:run
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

Switching to Jetty
------------------
What if you preferred Jetty over Tomcat? They're both compliant servlet containers, so it should be darn simple to switch. And it is!

Add this to your build file's list of dependencies:

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jetty</artifactId>
        </dependency>
```

Adding multipart upload support
-------------------------------
Imagine you also want to add file upload support. To do that, update your configuration and add a `MultipartConfigElement` to the application context.

    <@snippet path="src/main/java/hello/Application.java" prefix="complete"/>
    
> **Note:** A production version of `MultipartConfigElement` would not be empty but instead specify things like target upload path, file size upload limits, etc.

Re-run the app
--------------

Run the app again:

```sh
$ mvn package spring-boot:run
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

There is little change from the previous output, except there is no longer a `tomcatEmbeddedServletContainerFactory`. Instead, there is a new `jettyEmbeddedServletContainer`. 

There is also the `multipartConfigElement` you added. But along with it came a `multipartResolver` [courtesy of Spring Boot](https://github.com/SpringSource/spring-boot/blob/master/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/MultipartAutoConfiguration.java), a bean recommended to support file uploads with Spring MVC.

Other than that, everything else appears the same, as it should be. Most the beans listed above provide Spring MVC's production-grade features. Just swapping one part, the servlet container, and adding upload support shouldn't cause a system wide ripple.

Adding consumer-grade services
------------------------------
If you are building a web site for your business, there are probably some management services you are thinking about adding. Spring Boot provides several out of the box with its [actuator module][spring-boot-actuator] like health, audits, beans, and more.

Add this to your pom.xml:
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
```

Then restart the app:

```sh
$ mvn package spring-boot:run
```

You will see a new set of RESTful end points added to the application. These are management services provided by Spring Boot.

```sh
2013-08-01 08:03:42.592  INFO 43851 --- [lication.main()] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}" onto public java.util.Map<java.lang.String, java.lang.Object> org.springframework.boot.ops.web.BasicErrorController.error(javax.servlet.http.HttpServletRequest)
2013-08-01 08:03:42.592  INFO 43851 --- [lication.main()] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error],methods=[],params=[],headers=[],consumes=[],produces=[text/html],custom=[]}" onto public org.springframework.web.servlet.ModelAndView org.springframework.boot.ops.web.BasicErrorController.errorHtml(javax.servlet.http.HttpServletRequest)
2013-08-01 08:03:42.844  INFO 43851 --- [lication.main()] o.s.b.o.e.mvc.EndpointHandlerMapping     : Mapped URL path [/env] onto handler of type [class org.springframework.boot.ops.endpoint.EnvironmentEndpoint]
2013-08-01 08:03:42.844  INFO 43851 --- [lication.main()] o.s.b.o.e.mvc.EndpointHandlerMapping     : Mapped URL path [/health] onto handler of type [class org.springframework.boot.ops.endpoint.HealthEndpoint]
2013-08-01 08:03:42.844  INFO 43851 --- [lication.main()] o.s.b.o.e.mvc.EndpointHandlerMapping     : Mapped URL path [/beans] onto handler of type [class org.springframework.boot.ops.endpoint.BeansEndpoint]
2013-08-01 08:03:42.844  INFO 43851 --- [lication.main()] o.s.b.o.e.mvc.EndpointHandlerMapping     : Mapped URL path [/info] onto handler of type [class org.springframework.boot.ops.endpoint.InfoEndpoint]
2013-08-01 08:03:42.845  INFO 43851 --- [lication.main()] o.s.b.o.e.mvc.EndpointHandlerMapping     : Mapped URL path [/metrics] onto handler of type [class org.springframework.boot.ops.endpoint.MetricsEndpoint]
2013-08-01 08:03:42.845  INFO 43851 --- [lication.main()] o.s.b.o.e.mvc.EndpointHandlerMapping     : Mapped URL path [/trace] onto handler of type [class org.springframework.boot.ops.endpoint.TraceEndpoint]
2013-08-01 08:03:42.845  INFO 43851 --- [lication.main()] o.s.b.o.e.mvc.EndpointHandlerMapping     : Mapped URL path [/dump] onto handler of type [class org.springframework.boot.ops.endpoint.DumpEndpoint]
2013-08-01 08:03:42.845  INFO 43851 --- [lication.main()] o.s.b.o.e.mvc.EndpointHandlerMapping     : Mapped URL path [/shutdown] onto handler of type [class org.springframework.boot.ops.endpoint.ShutdownEndpoint]
```

They include:
- Errors
- [Environment](http://localhost:8080/env)
- [Health](http://localhost:8080/health)
- [Beans](http://localhost:8080/beans)
- [Info](http://localhost:8080/info)
- [Metrics](http://localhost:8080/metrics)
- [Trace](http://localhost:8080/trace)
- [Dump](http://localhost:8080/dump)
- Shutdown

You can invoke shutdown through curl.

```sh
$ curl -X POST localhost:8080/shutdown
```

The response shows that shutdown through REST is currently disabled by default (thank goodness!):
```sh
{"message":"Shutdown not enabled, sorry."}
```

For more details about each of these REST points and how you can tune their settings with an ``application.properties` file, feel free to dig into the [Spring Boot][spring-boot] project.

That is not all
---------------
That last example showed how Spring Boot makes it easy to wire beans you may not be aware you need. And it showed how to turn on convenient management services.

But Spring Boot does yet more. It supports not only traditional WAR file deployments, but also makes it easy to put together executable JARs thanks to Spring Boot's loader module. The various guides demonstrate this dual support through the `spring-boot-maven-plugin`. 

On top of that, Spring Boot also has Groovy support, allowing you to build web apps with as little as a single file.

Put the following code inside **app.groovy**:

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

Next, [install Spring Boot's CLI](https://github.com/SpringSource/spring-boot#installing-the-cli).

Finally, run it as follows:

```sh
$ spring run app.groovy
$ curl localhost:8080
Hello World!
```

Spring Boot dynamically adds key annotations to your code and leverages [Groovy Grapes](http://groovy.codehaus.org/Grape) to pull down needed libraries to make the app run.

Congratulations!
----------------
Spring Boot is powerful, but frankly too big to fit into a single guide. This is just a sampling of how much it can ramp up your development pace, letting you focus on business features and not worry about infrastructure.

As you read more of this site's getting started guides, you will see it used all over the place. It might not be obvious at first, because Spring Boot is so good at adding the things you need without getting in your way. But after using it for a bit, you may wonder how you lived without it.

[spring-boot]: https://github.com/SpringSource/spring-boot
[spring-boot-actuator]: https://github.com/SpringSource/spring-boot/blob/master/spring-boot-actuator/README.md
