FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=build/libs/chat-service-1.0.jar
COPY ${JAR_FILE} chatService.jar
ENTRYPOINT ["java","-jar","chatService.jar"]