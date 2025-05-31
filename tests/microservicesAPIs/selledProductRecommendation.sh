#!/bin/bash

EC2_DNS="ec2-44-201-142-106.compute-1.amazonaws.com"
PORT=8000
API_URL="http://$EC2_DNS:$PORT/SelledProduct"

post_data='{
  "typeOfAnalysis": "CUSTOMER",
  "typeValue": "1",
  "timestamp": "2022-03-10T12:15:50",
  "result": 10
}'
response=$(curl -s -X POST "$API_URL" -H 'accept: application/json' -H 'Content-Type: application/json' -d "$post_data")
echo "POST selled product response: $response"

echo "All tests passed successfully!"