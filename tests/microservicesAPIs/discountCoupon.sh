#!/bin/bash

API_URL='http://ec2-3-232-108-132.compute-1.amazonaws.com:8080/DiscountCupon'

# Step 1: Check if the list is empty
response=$(curl -s -X GET "$API_URL" -H 'accept: application/json' | tr -d '%')
echo "GET all cupons: $response"
[ "$response" = "[]" ] || { echo "Test failed: expected empty list"; exit 1; }

# Step 2: Create a discount cupon
response=$(curl -s -X POST "$API_URL" -H 'accept: application/json' -H 'Content-Type: application/json' -d '{"idLoyaltyCard": 1, "idsShops": [1,2,3], "discount": 20, "expirationDate": "2025-12-31T23:59:59"}')
echo "POST cupon response: $response"

# Step 3: Check if the cupon was created and get ID
response=$(curl -s -X GET "$API_URL" -H 'accept: application/json')
echo "GET all cupons: $response"
cupon_id=$(echo "$response" | grep -oP '"id":\s*\K\d+')
echo "Retrieved cupon ID from GET all: $cupon_id"
[ -z "$cupon_id" ] && { echo "Test failed: could not retrieve cupon ID"; exit 1; }

# Step 4: Get cupon by ID
response=$(curl -s -X GET "$API_URL/$cupon_id" -H 'accept: application/json')
echo "GET cupon by ID: $response"
echo "$response" | grep -q "\"id\":$cupon_id" || { echo "Test failed: cupon not found by ID"; exit 1; }

# Step 5: Delete the cupon
response=$(curl -s -X DELETE "$API_URL/$cupon_id" -H 'accept: application/json')
echo "DELETE cupon $cupon_id: $response"

# Step 6: Check if the list is empty again
response=$(curl -s -X GET "$API_URL" -H 'accept: application/json' | tr -d '%')
echo "GET all cupons after delete: $response"
[ "$response" = "[]" ] || { echo "Test failed: expected empty list after delete"; exit 1; }

echo "All tests passed successfully!"