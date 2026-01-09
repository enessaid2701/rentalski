FROM maven:3.8.6-eclipse-temurin-8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:8-jdk-alpine
RUN apk add --no-cache ttf-dejavu fontconfig
ENV LD_LIBRARY_PATH=/usr/lib
ENV TZ="Europe/Istanbul"
VOLUME /tmp
EXPOSE 80
COPY --from=build /app/target/*.jar app.jar
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.awt.headless=true -Dspring.profiles.active=prod -jar /app.jar" ]