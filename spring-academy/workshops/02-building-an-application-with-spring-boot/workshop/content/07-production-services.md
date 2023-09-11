If you are building a web site for your business, you probably need to
add some management services. Spring Boot provides several such services
(such as health, audits, beans, and more) with its [actuator
module](http://docs.spring.io/spring-boot/docs/3.1.1/reference/htmlsingle/#production-ready).

Add the following dependency to your `build.gradle` file:

    implementation 'org.springframework.boot:spring-boot-starter-actuator'

Then restart the application. This time, let’s use the command line.
First,stop the application using the Stop button (the red square) in the
dialog towards the top of the Editor:

![](images/editor-start-stop-dialog.png)

Now, in the **uppermost Terminal**, run the application as follows:

    $ ./gradlew bootRun

You should see that a new set of RESTful end points have been added to
the application. These are management services provided by Spring Boot.
The following listing shows typical output:

```text
org.springframework.boot.actuate.autoconfigure.availability.AvailabilityHealthContributorAutoConfiguration
org.springframework.boot.actuate.autoconfigure.availability.AvailabilityProbesAutoConfiguration
org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration
org.springframework.boot.actuate.autoconfigure.health.HealthContributorAutoConfiguration
org.springframework.boot.actuate.autoconfigure.health.HealthEndpointAutoConfiguration
org.springframework.boot.actuate.autoconfigure.health.HealthEndpointConfiguration
org.springframework.boot.actuate.autoconfigure.health.HealthEndpointWebExtensionConfiguration
org.springframework.boot.actuate.autoconfigure.health.HealthEndpointWebExtensionConfiguration$MvcAdditionalHealthEndpointPathsConfiguration
org.springframework.boot.actuate.autoconfigure.info.InfoContributorAutoConfiguration
...
org.springframework.boot.actuate.autoconfigure.web.servlet.ServletManagementContextAutoConfiguration
```

By default, the actuator exposes the following endpoints:

- `/actuator/health` - Gives a summary of the health of the
  application.

- `/actuator` - Lists all of the Actuator endpoints that are available
  in the application. You can configure your app the expose as many or
  few of the Actuator endpoints as you need.

> There is also an `/actuator/shutdown` endpoint, but, by default, it is
> visible only through JMX. To [enable it as an HTTP
> endpoint](http://docs.spring.io/spring-boot/docs/3.1.1/reference/htmlsingle/#production-ready-endpoints-enabling-endpoints),
> add `management.endpoint.shutdown.enabled=true` to your
> `application.properties` file and expose it with
> `management.endpoints.web.exposure.include=health,info,shutdown`. CAVEAT: you probably should not enable the shutdown endpoint for a
> publicly available application.

You can check the health of the application by running the following
command in the **lower Terminal**:

    $ curl http://localhost:8080/actuator/health
    {"status":"UP","groups":["liveness","readiness"]}

You can try also to invoke shutdown through curl, to see what happens
when you have not added the necessary line (shown in the preceding note)
to `application.properties`:

    $ curl -X POST localhost:8080/actuator/shutdown
    {"timestamp":"2023-06-30T20:24:25.564+00:00","status":404,"error":"Not Found","path":"/actuator/shutdown"}

Because we did not enable it, the requested endpoint is not available
(because the endpoint does not exist).

For more details about each of these REST endpoints and how you can tune
their settings with an `application.properties` file (in
`springboot/src/main/resources`), see the the [documentation about the
endpoints](http://docs.spring.io/spring-boot/docs/3.1.1/reference/htmlsingle/#production-ready-endpoints).
