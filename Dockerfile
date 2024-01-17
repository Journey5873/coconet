FROM openjdk:17
ARG JAR_FILE=build/libs/article-service-1.0.jar
COPY ${JAR_FILE} article-service.jar
ENTRYPOINT ["java","-jar","/article-service.jar"]