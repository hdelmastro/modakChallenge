# Modak Challenge

_API to send notifications_

## Starting 🚀

_Next you can see the steps to run the application locally and test it; also you can see the documentation of the endpoint and you can test it online._

### Requirements 📋

_Java JDK 17 should be installed.._

_And to compile the project do you have installed maven (https://maven.apache.org/download.cgi)._


### Install 🔧

_After cloning the repository, you need to go to the root folder, then run below command:_

```
mvn clean install
```
```
mvn spring-boot:run
```

## Test  ⚙️


_To test the service do you need to do a POST that below example_

## Local test
```
curl --location 'localhost:8080/api/notification/send' \
--header 'Content-Type: application/json' \
--data-raw '{
    "type":"STATUS",
    "message": "test message",     
    "recipient":"sfystd@ygdfsmail.com"
}'
```

## Online test (this is running on Railway)
```
curl --location 'https://modakchallenge-production.up.railway.app/api/notification/send' \
--header 'Content-Type: application/json' \
--data-raw '{
    "type":"STATUS",
    "message": "test message",     
    "recipient":"sfystd@ygdfsmail.com"
}'
```

## Documentation - Swagger 📖

* http://localhost:8080/swagger-ui/index.html


## Build with 🛠️

* [Maven](https://maven.apache.org/) - As dependency manager

## Autor ✒️

* **Héctor Edgardo del Mastro** - *Challenge Modak* - [hdelmastro](https://github.com/hdelmastro)

