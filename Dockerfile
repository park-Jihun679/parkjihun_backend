FROM eclipse-temurin:17-jdk-alpine

USER root

RUN cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime

ARG JAR_FILE=build/libs/wire-barley-0.0.1.jar

COPY ${JAR_FILE} app.jar

RUN mkdir -p /app/bin

ENV JAVA_TOOL_OPTIONS "${JAVA_TOOL_OPTIONS} \
-Duser.timezone=Asia/Seoul"

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]