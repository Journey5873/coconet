FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=build/libs/member-service-1.0.jar
COPY ${JAR_FILE} memberService.jar
ENTRYPOINT ["java","-jar","memberService.jar"]