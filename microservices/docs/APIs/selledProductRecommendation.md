# Selled Product API Documentation <!-- omit in toc -->

This documentation provides details on how to interact with the Selled Product API endpoints.

## POST /SelledProduct

Sends a message with the result of an analysis to a Kafka topic.

Must include a body like this:

```json
{
  "typeOfAnalysis": <string>,
  "typeValue": "<id-or-postal-code>",
  "timestamp": "<date-time>",
  "data": [
    {
      "product": <string>,
      "count":  <number>,
      "sumPrice":  <number>
    },
    {
      "product": <string>,
      "count":  <number>,
      "sumPrice":  <number>
    },
    ...
  ]
}
```

Where:

- `typeOfAnalysis` can be "LOYALTY_CARD", "DISCOUNT_COUPON", "SHOP" or "POSTAL_CODE"
- `typeValue` is the specific value for the analysis type (e.g., loyalty card ID, discount coupon ID, shop ID or postal code)

Depending on the `typeOfAnalysis`, the message will be sent to different Kafka topics:

- For "LOYALTY_CARD", it goes to `selledProductByLoyaltyCard`
- For "DISCOUNT_COUPON", it goes to `selledProductByCoupon`
- For "SHOP", it goes to `selledProductByShop`
- For "POSTAL_CODE", it goes to `selledProductByLocation`

<details>
<summary>Curl Example</summary>

```bash
curl -X 'POST' \
  'http://ec2-54-160-190-56.compute-1.amazonaws.com:8082/SelledProduct' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "typeOfAnalysis": "LOYALTY_CARD",
  "typeValue": "12",
  "timestamp": "2022-03-10T12:15:50",
  "data": [
    {
      "product": 10,
      "count": 10,
      "sumPrice": 10
    },
    {
      "product": 20,
      "count": 20,
      "sumPrice": 20
    }
  ]
}'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-34-201-0-73.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.

</details>
