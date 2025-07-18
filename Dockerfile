FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/PersonalFinancesService-0.4.0-SNAPSHOT.jar app.jar
COPY .env .
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]