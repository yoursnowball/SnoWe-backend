# start.sh
#!/bin/bash

CURRENT_PORT=$(cat /etc/nginx/service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "CURRENT_PORT is ${CURRENT_PORT}."

if [ ${CURRENT_PORT} -eq 8081 ];then
  TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
  TARGET_PORT=8081
else echo "There is no active SpringBootApplication now"
fi

echo "Deploy Script in ${TARGET_PORT}"

TARGET_PID=$(lsof -ti tcp:${TARGET_PORT})

if [ -z ${TARGET_PID} ]
then
 echo "There is no active SpringBoot Application"
else
 echo "kill $TARGET_PID"
 sudo kill -9 ${TARGET_PID}
 sleep 5
fi

nohup java -jar -DAPP_PORT=${TARGET_PORT} /home/ubuntu/snowe/build/libs/* > /home/ubuntu/nohup.out&
exit 0
