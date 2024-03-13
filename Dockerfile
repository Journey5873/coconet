FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=build/libs/article-service-1.0.jar
COPY ${JAR_FILE} articleService.jar
ENTRYPOINT ["java","-jar","articleService.jar"]