#!/bin/bash

docker build . -t docker.vpolkhovsky.net/memory
docker push  docker.vpolkhovsky.net/memory

kubectl apply -f memory.yaml
kubectl get services.serving.knative.dev
