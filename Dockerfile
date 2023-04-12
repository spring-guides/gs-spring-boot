FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY test-app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
