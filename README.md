# Software Used

1. Apache Kafka 2.13-2.7.0
2. Java 8
3. spring-boot 2.43

# Getting started

## 1 Create the kafka topic

Inside the kafka directory run:

``` bin/kafka-topics.sh --create --topic novice-players --bootstrap-server localhost:9092 ```

## 2 Run the sample application

Inside the root source directory run:

``` mvn spring-boot:run  ```

## 3 Submit players to process

In a new shell run:

``` curl -X POST "http://localhost:8080/players/" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"players\":[{\"name\":\"Sub zero\",\"type\":\"expert\"},{\"name\":\"Scorpion\",\"type\":\"novice\"},{\"name\":\"Reptile\",\"type\":\"meh\"}]}" ```

Or in a web browser use the swagger interface:

[localhost:8080/swagger-ui](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config)

## 4 Read events from the topic

Inside the kafka directory run:

``` bin/kafka-console-consumer.sh --topic novice-players --from-beginning --bootstrap-server localhost:9092 ```