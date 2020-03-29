FROM ubuntu:bionic

RUN apt-get update && apt-get -y install openjdk-11-jre

ADD target/spring-boot-0.0.1-SNAPSHOT.jar /

CMD java -jar /spring-boot-0.0.1-SNAPSHOT.jar
