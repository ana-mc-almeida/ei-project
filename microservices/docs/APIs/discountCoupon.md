# Discount Coupon API Documentation <!-- omit in toc -->

This documentation provides details on how to interact with the Discount Coupon API endpoints.

<details>
<summary>Table Of Contents</summary>

- [GET /DiscountCoupon](#get-discountcoupon)
- [GET /DiscountCoupon/{id}](#get-discountcouponid)
- [POST /DiscountCoupon](#post-discountcoupon)
- [DELETE /DiscountCoupon/{id}](#delete-discountcouponid)

</details>

## GET /DiscountCoupon

Retrieves all discount coupons.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'GET' \
  'http://ec2-3-221-149-118.compute-1.amazonaws.com:8080/DiscountCoupon' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-3-221-149-118.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.

</details>

## GET /DiscountCoupon/{id}

Retrieves a specific discount coupon by its unique ID.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'GET' \
  'http://ec2-3-221-149-118.compute-1.amazonaws.com:8080/DiscountCoupon/{id}' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-3-221-149-118.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.

</details>

## POST /DiscountCoupon

Creates a discount coupon for a given loyalty card and a list of shops.

Must include a body like this:

```json
{
  "idLoyaltyCard": <integer>,
  "idsShops": [<integer>, <integer>, ...],
  "discount": <integer>,
  "expirationDate": "YYYY-MM-DDTHH:MM:SS"
}
```

<details>
<summary>Curl Example</summary>

```bash
curl -X 'POST' \
  'http://ec2-3-221-149-118.compute-1.amazonaws.com:8080/DiscountCoupon' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
    "idLoyaltyCard": 1,
    "idsShops": [1, 2, 3],
    "discount": 20,
    "expirationDate": "2022-03-10T12:15:50"
}'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-3-221-149-118.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.

</details>

## DELETE /DiscountCoupon/{id}

Deletes a discount coupon by ID.

<details>
<summary>Curl Example</summary>

```bash
curl -X 'DELETE' \
  'http://ec2-3-221-149-118.compute-1.amazonaws.com:8080/DiscountCoupon/{id}' \
  -H 'accept: application/json'
```

> In this example, the EC2 instance is accessed via its public DNS name `ec2-3-221-149-118.compute-1.amazonaws.com` on port `8080`. Replace this with your actual instance address if different.

</details>
