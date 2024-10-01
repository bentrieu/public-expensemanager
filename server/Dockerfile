FROM maven:3.8.5-openjdk-17 AS maven

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package -DskipTests

FROM openjdk:17-alpine

WORKDIR /app

COPY --from=maven /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]