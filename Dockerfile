FROM alpine

WORKDIR "/app"

COPY ./Code/k-lima-gradle/ /app

EXPOSE 8080

RUN apk update && apk add openjdk8
RUN ./gradlew build

# sudo docker run --publish 8080:8080 --network="host" -it yssmcl/tutoria ./gradlew appStart
