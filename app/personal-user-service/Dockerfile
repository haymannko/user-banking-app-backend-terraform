FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy pre-built JAR
COPY target/personal-user-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 3501 7094

ENTRYPOINT ["java", "-jar", "app.jar"]
