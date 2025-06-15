# Loyalty Card API Documentation <!-- omit in toc -->

This documentation provides details on how to interact with the Loyalty Card API endpoints.

<details>
<summary>Table Of Contents</summary>

- [GET /Loyaltycard](#get-loyaltycard)
- [GET /Loyaltycard/{id}](#get-loyaltycardid)
- [GET /Loyaltycard/{idCustomer}/{idShop}](#get-loyaltycardidcustomeridshop)
- [POST /Loyaltycard](#post-loyaltycard)
- [DELETE /Loyaltycard/{id}](#delete-loyaltycardid)
- [DELETE /Loyaltycard/{idCustomer}/{idShop}](#delete-loyaltycardidcustomeridshop)

</details>

## GET /Loyaltycard

Retrieves a list of all loyalty cards.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'GET' \
  'http://ec2-44-222-220-109.compute-1.amazonaws.com:8080/Loyaltycard' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-44-222-220-109.compute-1.amazonaws.com` on port `8080`. Replace the public DNS with your actual instance address if different.

</details>

Returns a JSON array of loyalty card objects, each containing the following fields:

```json
[
  {
    "id": <integer>,
    "idCustomer": <integer>,
    "idShop": <integer>
  },
  ...
]
```

## GET /Loyaltycard/{id}

Retrieves a specific loyalty card by its unique ID.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'GET' \
  'http://ec2-44-222-220-109.compute-1.amazonaws.com:8080/Loyaltycard/{id}' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-44-222-220-109.compute-1.amazonaws.com` on port `8080`. Replace the public DNS with your actual instance address if different.

</details>

Returns a JSON object containing the loyalty card details:

```json
{
  "id": <integer>,
  "idCustomer": <integer>,
  "_idsShops": [<integer>,<integer>,<integer>]
}
```

## GET /Loyaltycard/{idCustomer}/{idShop}

Retrieves a loyalty card by customer ID and shop ID.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'GET' \
  'http://ec2-44-222-220-109.compute-1.amazonaws.com:8080/Loyaltycard/{idCustomer}/{idShop}' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-44-222-220-109.compute-1.amazonaws.com` on port `8080`. Replace the public DNS with your actual instance address if different.

</details>

Returns a JSON object containing the loyalty card details:

```json
{
  "id": <integer>,
  "idCustomer": <integer>,
  "idShop": <integer>
}
```

## POST /Loyaltycard

Creates a new loyalty card linking a customer to a shop.

Must include a body like this:

```json
{
  "idCustomer": <integer>,
  "idShop": <integer>
}
```

<details>
<summary>Curl Example</summary>

```bash
curl -X 'POST' \
  'http://ec2-44-222-220-109.compute-1.amazonaws.com:8080/Loyaltycard' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
    "idCustomer": 1,
    "idShop": 1
}'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-44-222-220-109.compute-1.amazonaws.com` on port `8080`. Replace the public DNS with your actual instance address if different.

</details>

Returns a JSON object with the created loyalty card ID:

```json
{
  "id": <integer>
}
```

## DELETE /Loyaltycard/{id}

Deletes a loyalty card by its unique ID.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'DELETE' \
  'http://ec2-44-222-220-109.compute-1.amazonaws.com:8080/Loyaltycard/{id}' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-44-222-220-109.compute-1.amazonaws.com` on port `8080`. Replace the public DNS with your actual instance address if different.

</details>

## DELETE /Loyaltycard/{idCustomer}/{idShop}

Deletes a loyalty card based on customer and shop IDs.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'DELETE' \
  'http://ec2-44-222-220-109.compute-1.amazonaws.com:8080/Loyaltycard/{idCustomer}/{idShop}' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-44-222-220-109.compute-1.amazonaws.com` on port `8080`. Replace the public DNS with your actual instance address if different.

</details>
