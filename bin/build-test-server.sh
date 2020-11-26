#!/usr/bin/sh
cd /home/workspace/ShengTangManage
git pull
mvn clean package -Dmaven.test.skip=true -P dev
rm -f /home/api/manage.shengtangdiet.com/test/stdiet-admin.jar
cp -f ./stdiet-admin/target/stdiet-admin.jar /home/api/manage.shengtangdiet.com/test/

kill -9 $(lsof -i tcp:8091 -t)
nohup java -jar /home/api/manage.shengtangdiet.com/test/stdiet-admin.jar