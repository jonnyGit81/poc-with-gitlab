FROM openjdk:8-jdk-alpine
VOLUME /app
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar
#run mkdir -p ./data/db/web/
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
