#!/bin/bash

module=${1-default}
port=${2-default}
env=${3-default}

echo "0. 在[$env]环境下重启模块[$module]"

echo "1.重新打包模块[$module]"
#./gradlew "${module}:clean"
#./gradlew "${module}:bootJar"
#./gradlew "${module}:bootJar" --refresh-dependencies

ptrestart "$module" "$port" "$env"
