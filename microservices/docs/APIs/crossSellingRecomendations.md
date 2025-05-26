# Cross-Selling Recommendation API Documentation <!-- omit in toc -->

This documentation provides details on how to interact with the Cross-Selling Recommendation API endpoints.

<details>
<summary>Table Of Contents</summary>

- [GET /CrossSellingRecommendation](#get-crosssellingrecommendation)
- [GET /CrossSellingRecommendation/{id}](#get-crosssellingrecommendationid)
- [POST /CrossSellingRecommendation](#post-crosssellingrecommendation)
- [DELETE /CrossSellingRecommendation/{id}](#delete-crosssellingrecommendationid)

</details>

## GET /CrossSellingRecommendation

Retrieves all cross-selling recommendations.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'GET' \
  'http://ec2-3-237-79-145.compute-1.amazonaws.com:8080/CrossSellingRecommendation' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-3-237-79-145.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.

</details>

## GET /CrossSellingRecommendation/{id}

Retrieves a specific cross-selling recommendation by its unique ID.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'GET' \
  'http://ec2-3-237-79-145.compute-1.amazonaws.com:8080/CrossSellingRecommendation/{id}' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-3-237-79-145.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.

</details>

## POST /CrossSellingRecommendation

Generates a cross-selling recommendation based on a loyalty card ID and a list of shop IDs.

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

## DELETE /CrossSellingRecommendation/{id}

Deletes a cross-selling recommendation by ID.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'DELETE' \
  'http://ec2-3-237-79-145.compute-1.amazonaws.com:8080/CrossSellingRecommendation/{id}' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-3-237-79-145.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.

</details>
