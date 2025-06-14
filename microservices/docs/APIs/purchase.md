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

No payload is required for this endpoint.

> <details>
> <summary>Curl Example</summary>
>
> ```bash
> curl -X 'GET' \
>   'http://ec2-3-221-160-179.compute-1.amazonaws.com:8080/Purchase' \
>   -H 'accept: application/json'
> ```
>
> In this example, the EC2 instance is accessed via its public DNS name `ec2-3-221-160-179.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.
>
> </details>

<br>

Returns a JSON array of purchase objects, each containing the following fields:

```json
[
  {
    "id": <integer>,
    "timestamp": "YYYY-MM-DDTHH:MM:SS",
    "price": <integer>,
    "product": "<string>",
    "supplier": "<string>",
    "shop_id": <integer>,
    "loyaltyCard_id": <integer>,
    "discountCoupon_id": <integer> or null
  },
  ...
]
```

## GET /Purchase/{id}

Retrieves a specific purchase by its unique ID.

No payload is required for this endpoint, but you must replace `{id}` with the actual purchase ID you want to retrieve.

> <details>
> <summary>Curl Example</summary>
>
> ```bash
> curl -X 'GET' \
>   'http://ec2-3-210-201-5.compute-1.amazonaws.com:8080/Purchase/{id}' \
>   -H 'accept: application/json'
> ```
>
> In this example, the EC2 instance is accessed via its public DNS name `ec2-3-210-201-5.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.
>
> </details>

<br>

Returns a JSON object containing the purchase details:

```json
{
  "id": <integer>,
  "timestamp": "YYYY-MM-DDTHH:MM:SS",
  "price": <integer>,
  "product": "<string>",
  "supplier": "<string>",
  "shop_id": <integer>,
  "loyaltyCard_id": <integer>,
  "discountCoupon_id": <integer> or null
}
```

## POST /Purchase/Consume

Starts consuming messages from a specific topic.

Must include a body like this:

```json
{
  "TopicName": "<loyaltyCard_id>-<shop_id>"
}
```

> <details>
> <summary>Curl Example</summary>
>
> ```bash
> curl -X 'POST' \
>   'http://ec2-3-221-160-179.compute-1.amazonaws.com:8080/Purchase/Consume' \
>   -H 'accept: text/plain' \
>   -H 'Content-Type: application/json' \
>   -d '{
>     "TopicName": "1-2"
> }'
> ```
>
> In this example, the EC2 instance is accessed via its public DNS name `ec2-3-221-160-179.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.
>
> </details>

<br>

Returns a plain text response indicating the topic name that is being consumed:

```
New worker started
```
