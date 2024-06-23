FROM openjdk:24-slim-bookworm

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

RUN mkdir -p public

COPY public/ public/

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]