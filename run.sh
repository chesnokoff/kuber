#!/bin/bash

kind create cluster

kubectl apply -f configmap.yaml

kubectl apply -f pod.yaml

kubectl apply -f deployment.yaml

kubectl apply -f service.yaml

kubectl apply -f daemonset.yaml

kubectl apply -f cron.yaml
