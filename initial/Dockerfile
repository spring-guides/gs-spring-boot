FROM maven:3.8.2-jdk-11 as BUILD
# Copy all project sources to app directory
COPY . /usr/src/app
# Set the working directory to /usr/src/app
WORKDIR /usr/src/app
# Package the project
RUN mvn -s /usr/share/maven/ref/settings-docker.xml package -DskipTests

# Starting from jre8 to build our onw in second step
FROM openjdk:11-jre-slim
# From fist step, copy build jar file to target directory
COPY --from=BUILD /usr/src/app/target/*.jar /opt/target/app.jar
# Set the working directory to /opt/target
WORKDIR /opt/target

# Run the application
ENTRYPOINT ["java","-jar", "app.jar"]
