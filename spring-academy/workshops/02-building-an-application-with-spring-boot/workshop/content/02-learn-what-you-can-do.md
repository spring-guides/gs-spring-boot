Spring Boot offers a fast way to build applications. It looks at your
classpath and at the beans you have configured, makes reasonable
assumptions about what you are missing, and adds those items. With
Spring Boot, you can focus more on business features and less on
infrastructure.

The following examples show what Spring Boot can do for you:

- Is Spring MVC on the classpath? There are several specific beans you
  almost always need, and Spring Boot adds them automatically. A
  Spring MVC application also needs a servlet container, so Spring
  Boot automatically configures embedded Tomcat.

- Is Jetty on the classpath? If so, you probably do NOT want Tomcat
  but instead want embedded Jetty. Spring Boot handles that for you.

- Is Thymeleaf on the classpath? If so, there are a few beans that
  must always be added to your application context. Spring Boot adds
  them for you.

These are just a few examples of the automatic configuration Spring Boot
provides. At the same time, Spring Boot does not get in your way. For
example, if Thymeleaf is on your path, Spring Boot automatically adds a
`SpringTemplateEngine` to your application context. But if you define
your own `SpringTemplateEngine` with your own settings, Spring Boot does
not add one. This leaves you in control with little effort on your part.

> Spring Boot does not generate code or make edits to your files. Instead,
> when you start your application, Spring Boot dynamically wires up beans
> and settings and applies them to your application context.
