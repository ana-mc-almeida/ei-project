#!/bin/bash

EC2_DNS="ec2-44-214-18-149.compute-1.amazonaws.com"
API_URL="http://$EC2_DNS:8081/CrossSellingRecommendation"

response=$(curl -s -X POST "$API_URL" -H 'accept: application/json' -H 'Content-Type: application/json' -d '{"idLoyaltyCard": 0, "idsShops": [1,2,3]}')
echo "POST recommendation response: $response"

echo "All tests passed successfully!"