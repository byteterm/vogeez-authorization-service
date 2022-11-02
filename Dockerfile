FROM gradle:7.5.1-jdk17
COPY . /
RUN gradle build
COPY build/libs/*.jar authentication.jar
FROM openjdk:17
CMD ["java", "-jar", "authentication.jar"]