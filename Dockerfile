FROM eclipse-temurin:17-jdk-alpine as build
#Using a local user instead of root
RUN addgroup appgroup; adduser --ingroup appgroup --disabled-password apprunner
USER apprunner

WORKDIR /temp/test-app

COPY test-app/mvnw .
COPY test-app/.mvn .mvn
COPY test-app/pom.xml .
COPY test-app/src src

RUN chmod +x mvnw
# RUN ./mvnw install -DskipTests
# RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# FROM eclipse-temurin:17-jdk-alpine
# VOLUME /tmp
# ARG DEPENDENCY=/temp/test-app/target/dependency
# COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
# COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
# COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
CMD ["./mvnw", "spring-boot:run"]