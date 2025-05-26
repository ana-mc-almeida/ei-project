# Selled Product API Documentation <!-- omit in toc -->

This documentation provides details on how to interact with the Selled Product API endpoints.

<details>
<summary>Table Of Contents</summary>

- [GET /SelledProduct](#get-selledproduct)
- [GET /SelledProduct/{id}](#get-selledproductid)
- [POST /SelledProduct](#post-selledproduct)
- [DELETE /SelledProduct/{id}](#delete-selledproductid)

</details>

## GET /SelledProduct

Retrieves all selled products analysis data.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'GET' \
  'http://ec2-34-201-0-73.compute-1.amazonaws.com:8080/SelledProduct' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-34-201-0-73.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.

</details>

## GET /SelledProduct/{id}

Retrieves a specific selled product analysis data by its unique ID.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'GET' \
  'http://ec2-34-201-0-73.compute-1.amazonaws.com:8080/SelledProduct/{id}' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-34-201-0-73.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.

</details>

## POST /SelledProduct

Creates a new selled product record with analysis data.

Must include a body like this:

```json
{
  "typeOfAnalysis": <string>,
  "typeValue": "<id-or-postal-code>",
  "timestamp": "<date-time>",
  "result": <number>
}
```

Where:

- `typeOfAnalysis` can be "LOYALTY_CARD", "CUSTOMER", "DISCOUNT_COUPON", "SHOP", "PRODUCT" or "POSTAL_CODE"
- `typeValue` is the specific value for the analysis type (e.g., loyalty card ID, customer ID, discount coupon ID, shop ID, product ID or postal code)
- `result` is the numerical result of the analysis

<details>
<summary>Curl Example</summary>

```bash
curl -X 'POST' \
  'http://ec2-34-201-0-73.compute-1.amazonaws.com:8080/SelledProduct' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
    "typeOfAnalysis": "CUSTOMER",
    "typeValue": "1",
    "timestamp": "2022-03-10T12:15:50",
    "result": 10
}'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-34-201-0-73.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.

</details>

## DELETE /SelledProduct/{id}

Deletes a selled product record by ID.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'DELETE' \
  'http://ec2-34-201-0-73.compute-1.amazonaws.com:8080/SelledProduct/{id}' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-34-201-0-73.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.

</details>
