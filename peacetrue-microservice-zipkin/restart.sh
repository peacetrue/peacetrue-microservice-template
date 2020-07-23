#!/bin/bash

env=${1-prod}

echo "0. 启动[$env]环境 zipkin 服务"
killbp 9411
[ "$env" == "prod" ] && host="peacetrue.cn" || host="localhost"
STORAGE_TYPE=elasticsearch ES_HOSTS="http://$host:9200/" ES_USERNAME=elastic ES_PASSWORD=password nohup java -Xmx256m -Xms256m -Xmn96m -Xss256k -jar zipkin.jar &

# http://localhost:9411/zipkin/
# http://peacetrue.cn:9411/zipkin/
