# switch.sh
# !/bin/bash
CURRENT_PORT=$(cat /etc/nginx/service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0
echo "> Nginx currently proxies to ${CURRENT_PORT}."
if [ ${CURRENT_PORT} -eq 8081 ]; then
  TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
  TARGET_PORT=8081
else
  echo "> No WAS is connected to nginx"
  exit 1
fi
# Change nginx Proxy
echo "set \$service_url http://127.0.0.1:${TARGET_PORT};" | tee /etc/nginx/service_url.inc

# Reload nginx
sudo service nginx reload
