# shop microservice API test script

This script tests the [shop microservice API](../../../microservices/docs/APIs/shop.md) by performing the following actions:

## 1. Get clean shop list
This step tests the `GET /Shop` endpoint to retrieve a clean list of shops.

## 2. Create a new shop
This step tests the `POST /Shop` endpoint to create a new shop with the following data:
```json
{
  "name": "string",
  "address": "string",
  "postalCode": "string"
}
```

## 3. Get all shops
This step tests the `GET /Shop` endpoint to retrieve all shops, including the one created in step 2.

## 4. Get the created shop
This step tests the `GET /Shop/{id}` endpoint to retrieve the shop created in the step 2.

## 5. Update the shop by ID
This step tests the `PUT /Shop/{id}` endpoint to update the shop created in step 2 with the following data:
```json
{
  "address": "aaaa",
  "postalCode": "ppppp",
  "name": "nnnnn"
}
```

## 6. Get the updated shop
This step tests the `GET /Shop/{id}` endpoint to retrieve the updated shop.

## 7. Delete the shop by ID
This step tests the `DELETE /Shop/{id}` endpoint to delete the shop created in step 2.

## 8. Get clean shop list again
This step tests the `GET /Shop` endpoint to retrieve a clean list of shops after the deletion in step 7.
