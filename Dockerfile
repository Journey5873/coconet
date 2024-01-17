FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=build/libs/discovery-service-1.0.jar
COPY ${JAR_FILE} discoveryService.jar
ENTRYPOINT ["java","-jar","discoveryService.jar"]