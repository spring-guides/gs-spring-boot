FROM eclipse-temurin:17-jdk-alpine as build

WORKDIR /opt/app
COPY test-app/mvnw .
COPY test-app/.mvn .mvn
COPY test-app/pom.xml .
COPY test-app/src src
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
RUN ./mvnw clean install
 
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /opt/app
EXPOSE 8080
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
#Using a local user instead of root
RUN addgroup appgroup; adduser --ingroup appgroup --disabled-password apprunner
USER apprunner
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar" ]