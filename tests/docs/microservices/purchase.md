# purchase microservice API test script

This script tests the [purchase microservice API](../../../microservices/docs/APIs/purchase.md) by performing the following actions:

## 1. Get clean purchase list
This step tests the `GET /Purchase` endpoint to retrieve a clean list of purchases.

## 2. Create a new purchase topic
This step tests the `POST /Purchase/Consume` endpoint to create a new purchase topic with the following data:
```json
{
  "TopicName": "1-continente"
}
```