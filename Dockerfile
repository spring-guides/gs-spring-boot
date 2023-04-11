FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE
COPY ${JAR_FILE} testapp.jar
ENTRYPOINT ["java","-jar","/testapp.jar"]