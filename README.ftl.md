<#assign project_id="gs-spring-boot">

What you'll build
-----------------

This guide provides an introduction to Spring Boot.


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

What does Spring Boot provide? At the core, it offers a much faster way to build applications because it looks at what is on your classpath and makes some reasonable assumptions.

For example:
- Got Spring MVC? There are a handful of needed beans people almost always use in that situation. But why stop there? A Spring MVC app 99.9% of the time needs a servlet container so Spring Boot will autoconfigure embedded Tomcat.
- Got Jetty? You probably do NOT want Tomcat, but instead embedded Jetty.
- Got Thymeleaf? There are a few beans that must always be added to your application context. Why should you have to track that?
- Doing multipart file uploads? The [MultipartConfigElement](http://docs.oracle.com/javaee/6/api/javax/servlet/MultipartConfigElement.html) is part of the servlet 3.0 spec and let's you define upload parameters in pure Java. Why should you have to worry about plugging one into a servlet? Define one in your application context and Spring Boot will snatch it up and plug it into Spring MVC's `DispatcherServlet`.

It doesn't stop there. These are just a few examples of the automatic configuration support Spring Boot provides. But they don't get in your way. Spring Boot may make assumptions and add a `SpringTemplateEngine` for your Thymeleaf-based application. But if you proceed to define your own `SpringTemplateEngine`, Spring Boot will step aside and prefer your choice.

Creating a simple web application
---------------------------------
You already have the base build file at the top. Next step is to create a web controller for a simple web application.

    <@snippet path="src/main/java/hello/HelloController.java" prefix="initial"/>
    
The class is flagged as a `@Controller` meaing it's ready for use by Spring MVC to handle web requests. `@RequestMapping` maps `GET /` to the `index()` method. It returns pure text thanks to the `@ResponseBody` annotation.

To make it executable, create an `Application` class:

    <@snippet path="src/main/java/hello/Application.java" prefix="initial"/>
    
- `@Configuration` tags the class as the source for defining beans for the application context.
- `@EnableAutoConfiguration` tells Spring Boot to get going and start adding beans based on classpath settings, other beans, and property settings.
- `@EnableWebMvc` signals Spring MVC that this application is a web application and to activate key behaviors for that.
- `@ComponentScanning` tells Spring to look for other components, configurations, and services in the the `hello` package, allowing it to find the `HelloController`.

The `main()` method uses Spring Boot's `SpringApplication.run()` method to launch an application. The `run()` method returns an `ApplicationContext` and this application then retrieves all the beans that were created either by your app or were automatically added thanks to Spring Boot.

To run it, execute:

```sh
$ mvn package spring-boot:run
```

You should see some output like this:

```txt
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
What if you preferred Jetty over Tomcat? They're both compliant choices, so it should be darn simple to switch. And it is!

Add this to your build file's list of dependencies:

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-up-jetty</artifactId>
        </dependency>
```

Adding multipart upload support
-------------------------------
You should also update your configuration and add a `MultipartConfigElement` to the application context.

    <@snippet path="src/main/java/hello/Application.java" prefix="complete"/>
    
> **Note:** This `MultipartConfigElement` may not have any settings other than an empty string. A production version would specify things like target upload path, file size upload limits, etc.

Re-run the app
--------------

Run the app again:

```sh
$ mvn package spring-boot:run
```

Now check out the output:

```txt
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

There is little change from the previous output, except there is no `tomcatEmbeddedServletContainerFactory`. Instead, there is a new `jettyEmbeddedServletContainer`. 

There is also the `multipartConfigElement` you added. But along with it came a `multipartResolver` [courtesy of Spring Boot](https://github.com/SpringSource/spring-boot/blob/master/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/MultipartAutoConfiguration.java).

Other than that, everything else appears the same, as it should be. Most the beans listed above provide Spring MVC's production-grade features. Just swapping one aspect, the container, and adding some upload support shouldn't cause a system wide ripple.

That is not all
---------------
That last example showed how Spring Boot makes it easy to wire beans you may not be aware you need. But Spring Boot does more than that. It supports not only traditional WAR file deployments, but also makes it easy to put together executable JARs thanks to Spring Boot's loader module. The various guides demonstrate this dual support through the `spring-boot-maven-plugin`.

Spring Boot provides more than a quick way to wire beans. Spring Boot's [actuator module](https://github.com/SpringSource/spring-boot/blob/master/spring-boot-actuator/README.md) adds common needed business components like health, metrics, audits, and other features.

Spring Boot also have Groovy support, allowing you to dynamically build web apps like this:
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
Spring Boot automatically laces the code with key annotations and Groovy Grapes to pull down needed libraries to make the app run. With Spring Boot's CLI tool, all you need do is:

```txt
$ spring run app.groovy
$ curl localhost:8080
Hello World!
```

Congratulations!
----------------
Spring Boot is powerful, but frankly too big to fit into a single guide. This is just a sampling.

As you read more of this site's getting started guides, you will see it used all over the place. It might not be obvious at first, because Spring Boot is so good at adding the things you need without getting in your way. But after using it for a bit, you may wonder how you lived without it.