## Build project
FROM maven:3.6.3-openjdk-11-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -B -f pom.xml clean package -DskipTests


FROM openjdk:11-jdk-slim
# Make port 8081 available to the world outside this container
EXPOSE 8080

VOLUME /tmp

COPY --from=build /workspace/target/central-erro-api-0.0.1-SNAPSHOT.jar central-erro-api-0.0.1-SNAPSHOT.jar

CMD ["sh", "-c", " java ${JAVA_OPTS} -jar central-erro-api-0.0.1-SNAPSHOT.jar"]
