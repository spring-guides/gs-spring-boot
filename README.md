This guide walks you through the process of building a simple web application with [Spring Boot][spring-boot].

What you'll build
-----------------

The guide provides a sampling of how Spring Boot helps you accelerate and facilitate app development. As you read more Spring getting started guides, you will see more use cases for Spring Boot.

What you'll need
----------------

 - About 15 minutes
 - A favorite text editor or IDE
 - [JDK 6][jdk] or later
 - [Maven 3.0][mvn] or later

[jdk]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[mvn]: http://maven.apache.org/download.cgi

How to complete this guide
--------------------------

Like all Spring's [Getting Started guides](/guides/gs), you can start from scratch and complete each step, or you can bypass basic setup steps that are already familiar to you. Either way, you end up with working code.

To **start from scratch**, move on to [Set up the project](#scratch).

To **skip the basics**, do the following:

 - [Download][zip] and unzip the source repository for this guide, or clone it using [git](/understanding/git):
`git clone https://github.com/springframework-meta/gs-spring-boot.git`
 - cd into `gs-spring-boot/initial`.
 - Jump ahead to [Learn what you can do with Spring Boot](#initial).

**When you're finished**, you can check your results against the code in `gs-spring-boot/complete`.
[zip]: https://github.com/springframework-meta/gs-spring-boot/archive/master.zip


<a name="scratch"></a>
Set up the project
------------------

First you set up a basic build script. You can use any build system you like when building apps with Spring, but the code you need to work with [Maven](https://maven.apache.org) and [Gradle](http://gradle.org) is included here. If you're not familiar with either, refer to [Building Java Projects with Maven](/guides/gs/maven) or [Building Java Projects with Gradle](/guides/gs/gradle/).

### Create the directory structure

In a project directory of your choosing, create the following subdirectory structure; for example, with `mkdir -p src/main/java/hello` on *nix systems:

    └── src
        └── main
            └── java
                └── hello

### Create a Maven POM

`pom.xml`
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.springframework</groupId>
    <artifactId>gs-spring-boot</artifactId>
    <version>0.1.0</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>0.5.0.BUILD-SNAPSHOT</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <properties>
        <start-class>hello.Application</start-class>
    </properties>

    <build>
        <plugins>
            <plugin> 
                <artifactId>maven-compiler-plugin</artifactId> 
                <version>2.3.2</version> 
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>spring-snapshots</id>
            <url>http://repo.springsource.org/libs-snapshot</url>
            <snapshots><enabled>true</enabled></snapshots>
        </repository>
    </repositories>
    <pluginRepositories>
        <pluginRepository>
            <id>spring-snapshots</id>
            <url>http://repo.springsource.org/libs-snapshot</url>
            <snapshots><enabled>true</enabled></snapshots>
        </pluginRepository>
    </pluginRepositories>
</project>
```

Learn what you can do with Spring Boot
--------------------------------------

Spring Boot offers a fast way to build applications. It looks at your classpath and at beans you have configured, makes reasonable assumptions about what you're missing, and adds it. With Spring Boot you can focus more on business features and less on infrastructure.

For example:
- Got Spring MVC? There are several specific beans you almost always need, and Spring Boot adds them automatically. A Spring MVC app also needs a servlet container, so Spring Boot automatically configures embedded Tomcat.
- Got Jetty? If so, you probably do NOT want Tomcat, but instead embedded Jetty. Spring Boot handles that for you.
- Got Thymeleaf? There are a few beans that must always be added to your application context; Spring Boot adds them for you.
- Doing multipart file uploads? [MultipartConfigElement](http://docs.oracle.com/javaee/6/api/javax/servlet/MultipartConfigElement.html) is part of the servlet 3.0 spec and lets you define upload parameters in pure Java. With Spring Boot, you don't have to plug a MultipartConfigElement into your servlet. Just define one in your application context and Spring Boot will plug it into Spring MVC's battle-tested `DispatcherServlet`.

These are just a few examples of the automatic configuration Spring Boot provides. At the same time, Spring Boot doesn't get in your way. For example, if Thymeleaf is on your path, Spring Boot adds a `SpringTemplateEngine` to your application context automatically. But if you define your own `SpringTemplateEnginer` with your own settings, then Spring Boot won't add one. This leaves you in control with little effort on your part.

> **Note:** Spring Boot doesn't generate code or make edits to your files. Instead, when you start up your application, Spring Boot dynamically wires up beans and settings and applies them to your application context.

Create a simple web application
---------------------------------
Now you can create a web controller for a simple web application.

`src/main/java/hello/HelloController.java`
```java
package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    
    @RequestMapping("/")
    public @ResponseBody String index() {
        return "Greetings from Spring Boot!";
    }
    
}
```
    
The class is flagged as a `@Controller`, meaning it's ready for use by Spring MVC to handle web requests. `@RequestMapping` maps `/` to the `index()` method. When invoked from a browser or using curl on the command line, the method returns pure text thanks to the `@ResponseBody` annotation.

Create an Application class
---------------------------
Here you create an `Application` class with the components:

`src/main/java/hello/Application.java`
```java
package hello;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan
public class Application {
    
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        
        System.out.println("Let's inspect the beans provided by Spring Boot:");
        
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

}
```
    
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

Switch from Tomcat to Jetty
---------------------------
What if you prefer Jetty over Tomcat? Jetty and Tomcat are both compliant servlet containers, so it should be easy to switch. With Spring Boot, it is!

Add this to your build file's list of dependencies:

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jetty</artifactId>
        </dependency>
```

Add multipart upload support
-------------------------------
Suppose you want to add file upload support. To do that, update your configuration by adding a `MultipartConfigElement` to the application context.

`src/main/java/hello/Application.java`
```java
package hello;

import java.util.Arrays;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan
public class Application {
    
    @Bean
    MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement("");
    }
    
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        
        System.out.println("Let's inspect the beans provided by Spring Boot:");
        
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

}
```
    
> **Note:** A production version of `MultipartConfigElement` would not be empty; it would specify target upload path, file size upload limits, and so forth.

Re-run the application
----------------------

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

Little changes from the previous output, except there is no longer a `tomcatEmbeddedServletContainerFactory`. Instead, there is a new `jettyEmbeddedServletContainer`. 

There is also the `multipartConfigElement` you added. But along with it came a `multipartResolver` [courtesy of Spring Boot](https://github.com/SpringSource/spring-boot/blob/master/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/MultipartAutoConfiguration.java), a bean recommended to support file uploads with Spring MVC.

Otherwise, everything is the same, as it should be. Most beans listed above provide Spring MVC's production-grade features. Simply swapping one part, the servlet container, and adding upload support shouldn't cause a system-wide ripple.

Add consumer-grade services
------------------------------
If you are building a web site for your business, you probably need to add some management services. Spring Boot provides several out of the box with its [actuator module][spring-boot-actuator], such as health, audits, beans, and more.

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

Whew! You probably don't want that until you are ready to turn on proper security settings, it at all.

For more details about each of these REST points and how you can tune their settings with an ``application.properties` file, check out the [Spring Boot][spring-boot] project.

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

But Spring Boot does yet more. It supports not only traditional WAR file deployments, but also makes it easy to put together executable JARs thanks to Spring Boot's loader module. The various guides demonstrate this dual support through the `spring-boot-maven-plugin`. 

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

> **Note:** It doesn't matter where the file is.

Next, [install Spring Boot's CLI](https://github.com/SpringSource/spring-boot#installing-the-cli).

Run it as follows:

```sh
$ spring run app.groovy
```

From a different terminal window:
```sh
$ curl localhost:8080
Hello World!
```

Spring Boot dynamically adds key annotations to your code and leverages [Groovy Grapes](http://groovy.codehaus.org/Grape) to pull down needed libraries to make the app run.

Summary
----------------
Congratulations! You built a simple web application with Spring Boot and learned how it can ramp up your development pace. 

[spring-boot]: https://github.com/SpringSource/spring-boot
[spring-boot-actuator]: https://github.com/SpringSource/spring-boot/blob/master/spring-boot-actuator/README.md
