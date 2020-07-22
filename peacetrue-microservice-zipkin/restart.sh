#!/bin/bash

echo "0. 启动 zipkin 服务"
killbp 9411
STORAGE_TYPE=elasticsearch ES_HOSTS=http://localhost:9200/ ES_USERNAME=elastic ES_PASSWORD=changeme nohup java -Xmx256m -Xms256m -Xmn96m -Xss256k -jar zipkin.jar &

# http://localhost:9411/zipkin/
