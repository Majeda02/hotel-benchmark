FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/api-grpc-java-0.0.1.jar app.jar
EXPOSE 8084 9090
ENTRYPOINT ["java","-jar","/app/app.jar"]
