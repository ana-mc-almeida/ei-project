# discountCoupon microservice API test script

This script tests the [discountCoupon microservice API](../../../microservices/docs/APIs/discountCoupon.md) by performing the following actions:

## 1. Get clean discount coupon list
This step tests the `GET /DiscountCoupon` endpoint to retrieve a clean list of discount coupons.

## 2. Create a new discount coupon
This step tests the `POST /DiscountCoupon` endpoint to create a new discount coupon with the following data:
```json
{
  "idLoyaltyCard": 1,
  "idsShops": [1,2,3],
  "discount": 20,
  "expirationDate": "2025-12-31T23:59:59"
}
```

## 3. Get all discount coupons
This step tests the `GET /DiscountCoupon` endpoint to retrieve all discount coupons, including the one created in step 2.

## 4. Get the created discount coupon
This step tests the `GET /DiscountCoupon/{id}` endpoint to retrieve the discount coupon created in the step 2.

## 5. Delete the discount coupon by ID
This step tests the `DELETE /DiscountCoupon/{id}` endpoint to delete the discount coupon created in step 2.

## 6. Get clean discount coupon list again
This step tests the `GET /DiscountCoupon` endpoint to retrieve a clean list of discount coupons after the deletion in step 5.