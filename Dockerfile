FROM openjdk:17-jdk-slim

MAINTAINER baeldung.com

COPY build/libs/kuber-1.0-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar" , "/app/app.jar"]