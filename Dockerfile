FROM eclipse-temurin:17-jdk-jammy

ENV TZ=Asia/Seoul

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "/app.jar"]