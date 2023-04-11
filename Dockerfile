FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app
 
COPY test-app/gradlew .
COPY test-app/gradle gradle
COPY test-app/build.gradle .
RUN ./gradlew dependencies
 
COPY test-app/src src
RUN ./gradlew build unpack -x test
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)
 
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","Application.class"]
