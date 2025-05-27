# crossSellingRecomendations microservice API test script

This script tests the [crossSellingRecomendations microservice API](../../../microservices/docs/APIs/crossSellingRecomendations.md) by performing the following actions:
## 1. Get clean cross-selling recommendations list
This step tests the `GET /CrossSellingRecommendation` endpoint to retrieve a clean list of cross-selling recommendations.

## 2. Create a new cross-selling recommendation
This step tests the `POST /CrossSellingRecommendation` endpoint to create a new cross-selling recommendation with the following data:
```json
{
  "idLoyaltyCard": 0,
  "idsShops": [1, 2, 3]
}
```

## 3. Get all cross-selling recommendations
This step tests the `GET /CrossSellingRecommendation` endpoint to retrieve all cross-selling recommendations, including the one created in step 2.

## 4. Get the created cross-selling recommendation
This step tests the `GET /CrossSellingRecommendation/{id}` endpoint to retrieve the cross-selling recommendation created in the previous step.

## 5. Delete the cross-selling recommendation by ID
This step tests the `DELETE /CrossSellingRecommendation/{id}` endpoint to delete the cross-selling recommendation created in step 2.

## 6. Get clean cross-selling recommendations list again
This step tests the `GET /CrossSellingRecommendation` endpoint to retrieve a clean list of cross-selling recommendations after the deletion in step 5.