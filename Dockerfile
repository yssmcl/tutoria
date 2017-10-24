FROM alpine

WORKDIR "/app"

COPY ./Code/k-lima-gradle/ /app

EXPOSE 8080

RUN apk update && apk add openjdk8
RUN ./gradlew build

# sudo docker build -t tutoria .
# sudo docker run --publish=8080:8080 --network="host" -it tutoria ./gradlew appStart
# --publish para servidor web
# --network para o banco de dados
