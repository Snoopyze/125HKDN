
FROM maven:3.9.6-eclipse-temurin-17 AS build

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY --from=build /app/target/app.jar app.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]