FROM openjdk:11-jdk-slim

# Make port 8081 available to the world outside this container
EXPOSE 8080

VOLUME /tmp

ADD target/central-erro-api-0.0.1-SNAPSHOT.jar central-erro-api-0.0.1-SNAPSHOT.jar

# Run the jar file
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar","central-erro-api-0.0.1-SNAPSHOT.jar"]