#!/bin/bash

kind create cluster --config istio/istio-kind.yaml

istioctl install --set profile=demo -y

sleep 3

kubectl get pods -n istio-system

sleep 3


# deployment
kubectl apply -f configmap.yaml
kubectl apply -f pod.yaml
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
kubectl apply -f daemonset.yaml
kubectl apply -f cron.yaml

# istio
kubectl apply -f istio/istio-gateway.yaml
kubectl apply -f istio/istio-virtualservice.yaml
kubectl apply -f istio/istio-destinationrule.yaml

# check isio
kubectl get virtualservices -n default
kubectl get gateways -n default
kubectl get destinationrules -n default