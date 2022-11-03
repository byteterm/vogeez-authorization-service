FROM gradle:7.5.1-jdk17 as builder

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:17
COPY --from=builder /home/gradle/src/build/libs/vogeez-authentication-service.jar /app/

WORKDIR /app
CMD ["java", "-jar", "vogeez-authentication-service.jar"]