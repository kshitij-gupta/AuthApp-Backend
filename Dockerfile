# Build Dependencies
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /workspace/app
COPY pom.xml .
RUN mvn dependency:go-offline

# package dependencies
COPY mvnw .
COPY .mvn .mvn
COPY src src
RUN mvn package -Dmaven.test.skip

FROM build AS test

# Setup Application
FROM openjdk:17-alpine
RUN addgroup -S authapp && adduser -S authapp -G authapp
USER authapp:authapp

COPY --from=build /workspace/app/target/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]