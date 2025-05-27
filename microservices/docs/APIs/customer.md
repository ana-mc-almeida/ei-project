# customer API Documentation <!-- omit in toc -->

This documentation provides details on how to interact with the Customer API endpoints.

<details>
<summary>Table Of Contents</summary>

- [GET /Customer](#get-customer)
- [GET /Customer/{id}](#get-customerid)
- [POST /Customer](#post-customer)
- [PUT /Customer/{id}](#put-customerid)
- [DELETE /Customer/{id}](#delete-customerid)

</details>

## GET /Customer

Retrieves a list of all customers.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'GET' \
  'http://ec2-44-193-226-242.compute-1.amazonaws.com:8080/Customer' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-44-193-226-242.compute-1.amazonaws.com` on port `8080`. Don't forget to replace this with your actual instance's public DNS or IP address.

</details>

## GET /Customer/{id}

Retrieves a specific customer by ID.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'GET' \
  'http://ec2-44-193-226-242.compute-1.amazonaws.com:8080/Customer/{id}' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-44-193-226-242.compute-1.amazonaws.com` on port `8080`. Don't forget to replace this with your actual instance's public DNS or IP address.

</details>

## POST /Customer

Creates a new customer.

Must include a body like this:

```json
{
  "FiscalNumber": <integer>,
  "address": <string>,
  "postalCode": <string>,
  "name": <string>
}
```

<details>
<summary>Curl Example</summary>

```bash
curl -X 'POST' \
  'http://ec2-44-193-226-242.compute-1.amazonaws.com:8080/Customer' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
    "FiscalNumber": 0,
    "address": "string",
    "postalCode": "string",
    "name": "string"
}'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-44-193-226-242.compute-1.amazonaws.com` on port `8080`. Don't forget to replace this with your actual instance's public DNS or IP address.

</details>

## PUT /Customer/{id}

Updates an existing customer by ID.

Must include a body like this:

```json
{
  "FiscalNumber": <integer>,
  "address": <string>,
  "postalCode": <string>,
  "name": <string>
}
```

<details>
<summary>Curl Example</summary>

```bash
curl -X 'PUT' \
  'http://ec2-44-193-226-242.compute-1.amazonaws.com:8080/Customer/{id}' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
    "FiscalNumber": 1,
    "address": "aaaaaa",
    "postalCode": "ppppp",
    "name": "nnnnn"
}'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-44-193-226-242.compute-1.amazonaws.com` on port `8080`. Don't forget to replace this with your actual instance's public DNS or IP address.

</details>

## DELETE /Customer/{id}

Deletes a customer by ID.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'DELETE' \
  'http://ec2-44-193-226-242.compute-1.amazonaws.com:8080/Customer/{id}' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-44-193-226-242.compute-1.amazonaws.com` on port `8080`. Don't forget to replace this with your actual instance's public DNS or IP address.

</details>
