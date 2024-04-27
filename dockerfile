#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml -DJAR_NAME=build clean package -DskipTests
EXPOSE 8081
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","-Dserver.port=8081","/home/app/target/build.jar"]