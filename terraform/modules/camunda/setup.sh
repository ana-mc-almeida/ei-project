#!/bin/bash
until curl -s -o /dev/null -w "%%{http_code}" http://${publicdns_camunda}:8080/engine-rest/engine | grep -q "200"; do
  sleep 5
done
echo "Camunda is up and running!"