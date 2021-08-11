FROM alpine:latest
EXPOSE 8081
RUN apk update
RUN apk upgrade --update-cache --available
RUN apk add openjdk11 && apk add --no-cache librdkafka
VOLUME /tmp
ADD target/orchestrator-0.0.1-SNAPSHOT.jar app.jarg
ENTRYPOINT ["java","-jar","/app.jar"]

