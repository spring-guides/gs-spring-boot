FROM openjdk:11
EXPOSE 8080
ADD target/test-app.jar test-app.jar
ENTRYPOINT ["java","-jar","/test-app.jar"]