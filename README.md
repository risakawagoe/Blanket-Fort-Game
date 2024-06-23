# Blanket Fort Game
A REST API for a Blanket Fort Game developed in Java Spring Boot

>Live demo available [here](https://springboot-game-server-mch4m52ieq-uc.a.run.app/) :sparkles:

![screenshot](./screenshot.png)

## Technologies used
* [![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/en/)
* [![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=Spring&logoColor=white)](https://spring.io/projects/spring-boot)
* [![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white)](https://www.docker.com/)
* [![Google Cloud Platform](https://img.shields.io/badge/Google_Cloud_Platform-4285F4?style=flat-square&logo=google-cloud&logoColor=white)](https://cloud.google.com/)

### Local Installation
Open the project in intellij and run Application.main().

Then navigate to http://localhost:8080


### Containerization with Docker
>Latest stable version [springboot-game-server:v1.0.2]

Run the following commands at the root directory.
```
./gradlew build
docker build -t <username>/<imagenamge>:<tag> .
```