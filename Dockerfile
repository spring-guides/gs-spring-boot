FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app
 
COPY test-app/gradlew .
COPY test-app/gradle gradle
COPY test-app/build.gradle .
RUN ./gradlew bootJar 
RUN ls -a && cd build/libs && ls -a
COPY build/libs/*.jar *.jar
ENTRYPOINT ["java","-jar","/*.jar"]
