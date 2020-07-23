#!/bin/bash

env="native,runmod"
./restart.sh peacetrue-microservice-config-center 8888 "$env" && sleep 20s
#if [ $env == "prod" ]; then
#  peacetrue-microservice-zipkin/restart4prod.sh
#else
#  peacetrue-microservice-zipkin/restart.sh
#fi
#./restart.sh peacetrue-microservice-config-center 8888 "$env" && sleep 20s
#./restart.sh peacetrue-microservice-registry-center 8761 "$env" && sleep 20s
##./restart.sh peacetrue-microservice-authorization-server 8510 "$env"
#./restart.sh peacetrue-microservice-resource-server 8520 "$env"
#./restart.sh peacetrue-microservice-client-inner 8530 "$env"
#if [ $env == "prod" ]; then
#  ./restart.sh peacetrue-microservice-gateway 443 "$env"
#else
#  ./restart.sh peacetrue-microservice-gateway 80 "$env"
#fi
#./restart.sh peacetrue-microservice-client-outer 8531 "$env"
