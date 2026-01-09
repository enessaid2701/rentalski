FROM openjdk:8-jdk-alpine
RUN apk add ttf-dejavu
ENV LD_LIBRARY_PATH /usr/lib
ENV TZ="Europe/Istanbul"
VOLUME /tmp
EXPOSE 80
ADD target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.awt.headless=true -Dspring.profiles.active=prod -jar /app.jar" ]