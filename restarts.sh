#!/bin/bash

env="prod"

./restart.sh peacetrue-microservice-config-center 8888 "$env"
sleep 20s
./restart.sh peacetrue-microservice-registry-center 8761 "$env"
sleep 20s
#./restart.sh peacetrue-microservice-authorization-server 8510 "$env"
./restart.sh peacetrue-microservice-resource-server 8520 "$env"
./restart.sh peacetrue-microservice-client-inner 8530 "$env"
./restart.sh peacetrue-microservice-gateway 443 "$env"
./restart.sh peacetrue-microservice-client-outer 8532 "$env"
