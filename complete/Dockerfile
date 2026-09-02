FROM gradle:8.14-jdk17 AS builder

WORKDIR /app

COPY . .

RUN gradle build -x test

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]