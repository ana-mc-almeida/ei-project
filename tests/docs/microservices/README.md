# microservicesAPIs tests documentation

> TODO: this documentation and all the documentation is outdated and needs to be updated.

### Prerequisites

- bash
- curl
- grep

### Running the tests

Before running the tests, ensure that:

- The microservice APIs that you want to test are running.
- The EC2 instance DNS name is set correctly in the test scripts.
- The test scripts have execute permissions.

You can automatically update the EC2 instance DNS name in the test scripts by running the following command:

```bash
./tests/microservicesAPIs/update-dns.sh
```

To run the tests from the project root, execute the following command in your terminal:

```bash
./tests/microservicesAPIs/<test_script_name>.sh
```

### Available Test Scripts

- [crossSellingRecomendations.sh](../../microservicesAPIs/crossSellingRecommendations.sh): Tests the cross-selling recommendations microservice API.
  - Check this [test documentation](crossSellingRecommendations.md) for more details.
- [customers.sh](../../microservicesAPIs/customer.sh): Tests the customer microservice API.
  - Check this [test documentation](customer.md) for more details.
- [discountCoupon.sh](../../microservicesAPIs/discountCoupon.sh): Tests the discount coupon microservice API.
  - Check this [test documentation](discountCoupon.md) for more details.
- [loyaltyCard.sh](../../microservicesAPIs/loyaltyCard.sh): Tests the loyalty card microservice API.
  - Check this [test documentation](loyaltyCard.md) for more details.
- [ollama.sh](../../microservicesAPIs/ollama.sh): Tests the Ollama microservice
  - Check this [test documentation](ollama.md) for more details.
- [purchase.sh](../../microservicesAPIs/purchase.sh): Tests the purchase microservice API.
  - Check this [test documentation](purchase.md) for more details.
- [selledProductRecommendation.sh](../../microservicesAPIs/selledProductRecommendation.sh): Tests the selled product recommendations microservice API.
  - Check this [test documentation](selledProductRecommendations.md) for more details.
- [shops.sh](../../microservicesAPIs/shops.sh): Tests the shops microservice API.
  - Check this [test documentation](shops.md) for more details.
