#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG JAR_FILE=/web/poc-web-app/target/*.jar
#ADD ${JAR_FILE} app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/app.jar"]


FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
ARG SPRING_ACTIVE_PROFILE
MAINTAINER Jonny
COPY pom.xml /build/
COPY web /build/web/
WORKDIR /build/
RUN mvn clean install -Dspring.profiles.active=$SPRING_ACTIVE_PROFILE && mvn package -B -e -Dspring.profiles.active=$SPRING_ACTIVE_PROFILE
FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/web/poc-web-app/target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
