# Build stage
FROM maven:3.8.3-openjdk-17 AS build-stage

## Change directory
WORKDIR /app

## Download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

## Build code
COPY src ./src
RUN mvn install -DskipTests=true

# Run stage
FROM alpine:3.19 AS run-stage
MAINTAINER com.HungTran
RUN apk add openjdk17

## Change directory
WORKDIR /app

## Create non-root user
RUN adduser -D dkhp_backend
RUN chown -R dkhp_backend. /app
USER dkhp_backend

## Copy war file and run app
COPY --from=build-stage /app/target/demo-0.0.1-SNAPSHOT.war dkhp_backend.war
ENTRYPOINT ["java","-jar","dkhp_backend.war", "--spring.profiles.active=docker"]

## Expose port 8080
EXPOSE 8080
