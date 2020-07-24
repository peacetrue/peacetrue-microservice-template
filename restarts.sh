#!/bin/bash

env=${1-prod}
#env=${1-default,native,cmd,rabbitmqLog}
cd peacetrue-microservice-zipkin && ./restart.sh "$env" && cd ..
./restart.sh peacetrue-microservice-config-center 8888 "$env" && sleep 20s
./restart.sh peacetrue-microservice-registry-center 8761 "$env" && sleep 20s
./restart.sh peacetrue-microservice-admin-server 8500 "$env"
#./restart.sh peacetrue-microservice-authorization-server 8510 "$env"
./restart.sh peacetrue-microservice-resource-server 8520 "$env"
./restart.sh peacetrue-microservice-client-inner 8530 "$env"
[ "$env" == "prod" ] && PORT=443 || PORT=80
./restart.sh peacetrue-microservice-gateway $PORT "$env"
./restart.sh peacetrue-microservice-client-outer 8531 "$env"
