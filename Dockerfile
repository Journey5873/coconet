FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=build/libs/member-service-1.0.jar
COPY ${JAR_FILE} memberService.jar
RUN mkdir -p /src/main/resources/memberProfilePics
ENTRYPOINT ["java","-jar","memberService.jar"]