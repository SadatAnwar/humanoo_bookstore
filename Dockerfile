# Use a build image to build the project
FROM maven:3-jdk-11 AS build

ENV DB_HOST localhost
ENV DB_PORT 5432

WORKDIR /usr/bookstore

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ ./src/
COPY wait-for-it.sh ./wait-for-it.sh

RUN mvn package

# final base image to execute
FROM java:8-jdk-alpine

# Install bash to run the wait-for-it script
RUN apk add --no-cache bash

# set deployment directory
WORKDIR /usr/app

# copy over the built artifact from the maven image
COPY --from=build /usr/bookstore/target/bookstore-*.jar ./
COPY --from=build /usr/bookstore/wait-for-it.sh ./wait-for-it.sh

ENTRYPOINT ./wait-for-it.sh ${DB_HOST}:${DB_PORT} -- java -jar bookstore-1.0-SNAPSHOT.jar
