#!/bin/bash

./gradlew clean build

docker image build -t chesnokoff/app:v1 .

docker login

docker push chesnokoff/app:v1