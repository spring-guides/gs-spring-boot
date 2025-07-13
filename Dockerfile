FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/java-SpringBoot-Test.jar /app/ava-SpringBoot-Test.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
