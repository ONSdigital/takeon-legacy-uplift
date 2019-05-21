#!/bin/bash

aws eks --region eu-west-2 update-kubeconfig --name $1
kubectl apply -f config_map_aws_auth.yaml
helm init
kubectl create serviceaccount --namespace kube-system tiller
kubectl create clusterrolebinding tiller-cluster-rule --clusterrole=cluster-admin --serviceaccount=kube-system:tiller
kubectl patch deploy --namespace kube-system tiller-deploy -p '{"spec":{"template":{"spec":{"serviceAccount":"tiller"}}}}'
kubectl create namespace $2