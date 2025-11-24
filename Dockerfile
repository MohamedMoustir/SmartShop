FROM eclipse-temurin:17-jdk-focal AS builder

WORKDIR /app

COPY pom.xml .


COPY target/smartshop-backend-0.0.1-SNAPSHOT.jar app.jar

FROM eclipse-temurin:17-jre-focal

WORKDIR /opt/app

COPY --from=builder /app/app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]