FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=build/libs/gateway-service-1.0.jar
COPY ${JAR_FILE} gatewayService.jar
ENTRYPOINT ["java","-jar","gatewayService.jar"]
