FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=build/libs/member-service-1.0.jar
ARG MEMBER_PIC_FILE=src/main/resources/memberProfilePics
COPY ${JAR_FILE} memberService.jar
COPY ${MEMBER_PIC_FILE} memberProfilePics
ENTRYPOINT ["java","-jar","memberService.jar"]