#!/bin/bash

git checkout develop-v2 &&
git fetch &&
git pull &&
mvn clean package &&
docker build -t vsq/contract . &&
docker stop contract &&
docker rm contract &&
docker run -d --restart=always --name contract --volume contract:/var -p 8010:8010 -e JAVA_OPTS="-Dspring.profiles.active=dev -Dspring.datasource.password=postgres -Deureka.client.service-url.defaultZone=http://192.168.12.5:8000/registry/eureka -Dspring.datasource.url=jdbc:postgresql://172.17.0.1:5432/ -Deureka.instance.ip-address=192.168.12.3" vsq/contract
