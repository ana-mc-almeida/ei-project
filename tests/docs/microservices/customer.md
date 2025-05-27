# customer microservice API test script

This script tests the [customer microservice API](../../../microservices/docs/APIs/customer.md) by performing the following actions:

## 1. Get clean customer list

This step tests the `GET /Customer` endpoint to retrieve a clean list of customers.

## 2. Create a new customer
This step tests the `POST /Customer` endpoint to create a new customer with the following data:
```json
{
  "FiscalNumber": 0,
  "address": "string",
  "postalCode": "string",
  "name": "string"
}
```

## 3. Get all customers
This step tests the `GET /Customer` endpoint to retrieve all customers, including the one created in step 2.

## 4. Get the created customer
This step tests the `GET /Customer/{id}` endpoint to retrieve the customer created in the previous step.

## 5. Update the customer by ID
This step tests the `PUT /Customer/{id}` endpoint to update the customer created in step 2 with the following data:
```json
{
  "FiscalNumber": 1,
  "address": "aaaaaa",
  "postalCode": "ppppp",
  "name": "nnnnn"
}
```

## 6. Get the updated customer
This step tests the `GET /Customer/{id}` endpoint to retrieve the updated customer.

## 7. Delete the customer by ID
This step tests the `DELETE /Customer/{id}` endpoint to delete the customer created in step 2.

## 8. Get clean customer list again
This step tests the `GET /Customer` endpoint to retrieve a clean list of customers after the deletion in step 7.

