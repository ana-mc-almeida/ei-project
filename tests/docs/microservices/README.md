# microservicesAPIs tests documentation

### Prerequisites

- bash
- curl
- grep

### Running the tests

Before running the tests, ensure that:
- The microservice APIs that you want to test are running.
- The EC2 instance DNS name is set correctly in the test scripts.
- The test scripts have execute permissions.

To run the tests from the project root, execute the following command in your terminal:
```bash
./tests/microservicesAPIs/<test_script_name>.sh
```

### Available Test Scripts

- [customers.sh](../../microservicesAPIs/customer.sh): Tests the customer microservice API.
  - Check this [test documentation](customer.md) for more details.

TODO: add the other microservices test scripts