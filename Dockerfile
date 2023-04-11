FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app
 
COPY test-app/gradlew .
COPY test-app/gradle gradle
COPY test-app/build.gradle .
RUN ./gradlew bootJar && cd build/libs && ls -a
COPY build/*.jar *.jar
ENTRYPOINT ["java","-jar","/*.jar"]
