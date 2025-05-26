# selledProductRecommendation microservice API test script

This script tests the [selledProductRecommendation microservice API](../../../microservices/docs/APIs/selledProductRecommendation.md) by performing the following actions:

## 1. Get clean selled product recommendation list
This step tests the `GET /SelledProduct` endpoint to retrieve a clean list of selled product recommendations.

## 2. Create a new selled product recommendation
This step tests the `POST /SelledProduct` endpoint to create a new selled product recommendation with the following data:
```json
{
  "typeOfAnalysis": "CUSTOMER",
  "typeValue": "CUSTOMER",
  "timestamp": "2022-03-10T12:15:50",
  "result": 10
}
```

## 3. Get all selled product recommendations
This step tests the `GET /SelledProduct` endpoint to retrieve all selled product recommendations, including the one created in step 2.

## 4. Get the created selled product recommendation
This step tests the `GET /SelledProduct/{id}` endpoint to retrieve the selled product recommendation created in the step 2.

## 5. Delete the selled product recommendation by ID
This step tests the `DELETE /SelledProduct/{id}` endpoint to delete the selled product recommendation created in step 2.

## 6. Get clean selled product recommendation list again
This step tests the `GET /SelledProduct` endpoint to retrieve a clean list of selled product recommendations after the deletion in step 5.

