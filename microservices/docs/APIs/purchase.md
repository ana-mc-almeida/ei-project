# Purchase API Documentation <!-- omit in toc -->

This documentation provides details on how to interact with the Purchase API endpoints.

<details>
<summary>Table Of Contents</summary>

- [GET /Purchase](#get-purchase)
- [GET /Purchase/{id}](#get-purchaseid)
- [POST /Purchase/Consume](#post-purchaseconsume)

</details>

## GET /Purchase

Retrieves a list of all purchases.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'GET' \
  'http://ec2-3-221-160-179.compute-1.amazonaws.com:8080/Purchase' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-3-221-160-179.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.

</details>

## GET /Purchase/{id}

Retrieves a specific purchase by its unique ID.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'GET' \
  'http://ec2-3-210-201-5.compute-1.amazonaws.com:8080/Purchase/{id}' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-3-210-201-5.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.

</details>

## POST /Purchase/Consume

Starts consuming messages from a specific topic.

Must include a body like this:

```json
{
  "TopicName": "<string>"
}
```

<details>
<summary>Curl Example</summary>

```bash
curl -X 'POST' \
  'http://ec2-3-221-160-179.compute-1.amazonaws.com:8080/Purchase/Consume' \
  -H 'accept: text/plain' \
  -H 'Content-Type: application/json' \
  -d '{
    "TopicName": "1-continente"
}'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-3-221-160-179.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.

</details>
