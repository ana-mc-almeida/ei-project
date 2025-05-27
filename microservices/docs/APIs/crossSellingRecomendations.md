# Cross-Selling Recommendation API Documentation <!-- omit in toc -->

This documentation provides details on how to interact with the Cross-Selling Recommendation API endpoints.

## POST /CrossSellingRecommendation

Generates a cross-selling recommendation based on a loyalty card ID and a list of shop IDs, sending it to the `crossSellingRecommendation` Kafka topic.

Must include a body like this:

```json
{
  "idLoyaltyCard": <integer>,
  "idsShops": [<integer>, <integer>, ...]
}
```

<details>
<summary>Curl Example</summary>

```bash
curl -X 'POST' \
  'http://ec2-3-237-79-145.compute-1.amazonaws.com:8080/CrossSellingRecommendation' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
    "idLoyaltyCard": 0,
    "idsShops": [1, 2, 3]
}'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-3-237-79-145.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.

</details>
