#!/bin/bash

kind create cluster

sleep 5

kubectl apply -f configmap.yaml

sleep 5

kubectl apply -f pod.yaml

# init serviceaccount
sleep 5

kubectl get pods

kubectl describe pod app-pod

kubectl port-forward pod/app-pod 8090:8090

#PORT_FORWARD_PID=$!
#
#sleep 5
#
#curl http://localhost:8090
#
#kill $PORT_FORWARD_PID
#
#kind delete cluster
