As you've already seen, you can run the application from the command line by executing `./gradlew bootRun`. You can also build a single executable JAR file that contains all the necessary dependencies, classes, and resources. Building an executable jar makes it easy to ship, version, and deploy the service as an application throughout the development lifecycle, across different environments, and so forth.

You can build the JAR file by using `./gradlew build` and then run the JAR file, as follows:

```console
[~/springboot] $ ./gradlew build
...
BUILD SUCCESSFUL in 40s
[~/springboot] $ java -jar build/libs/springboot-0.0.1-SNAPSHOT.jar
```

The JAR you just built is suitable for deployment to any cloud environment. On the other hand, you might need to [build a classic WAR file](https://spring.io/guides/gs/convert-jar-to-war/), which can be run in a Java servlet container.
