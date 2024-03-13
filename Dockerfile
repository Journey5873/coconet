FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=build/libs/article-service-1.0.jar
COPY ${JAR_FILE} articleService.jar
COPY ../../files/article-encryptor-password.txt src/main/resources/article-encryptor-password.txt
ENTRYPOINT ["java","-jar","articleService.jar"]