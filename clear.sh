#!/bin/bash

# 停止所有端口
./kills.sh
# 清除所有日志
rm -rf `find . -name "root*.log" -o -name nohup.out`

