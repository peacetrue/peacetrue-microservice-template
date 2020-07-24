#!/bin/bash

env=${1-prod}
#env=${1-default}

echo "0. 启动[$env]环境 uaa 服务"

basePath=$(pwd)
logPath="$basePath"
configPath="$basePath"

killbp 8080
killbp 8443
killbp 8005

[ "$env" == "prod" ] && logPath="$basePath/prod" && configPath="$basePath/prod"
[ "$env" == "4prod" ] && logPath="$basePath" && configPath="$basePath/prod"
echo "$logPath | $configPath"
JAVA_OPTS="-server -Xmx256m -Xms256m -Xmn96m -Xss256k -Dlogging.config=$basePath/log4j2.properties -DUAA_CONFIG_PATH=$basePath" apache-tomcat-9.0.37/bin/startup.sh

# http://localhost:8080/uaa/
# https://peacetrue.cn:8443/uaa/
