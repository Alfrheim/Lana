FROM openjdk:8-jdk-alpine
#we create a user for security reasons
RUN addgroup -S lana && adduser -S lana -G lana
USER lana:lana
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]