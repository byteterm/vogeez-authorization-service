FROM nginx:latest AS GradleBuild

COPY ./ ./

RUN gradle build

COPY --from=GradleBuild /build/libs/vogeez-authentication-service.jar /vogeez-authentication-service.jar

FROM openjdk:17

CMD ["java", "-jar", "/vogeez-authentication-service.jar"]