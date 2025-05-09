terraform {
    required_version = ">= 1.0.0, < 2.0.0"
    required_providers {
        aws = {
            source = "hashicorp/aws"
            version = "~> 4.0"
        }
    }

      backend "s3" {
        bucket = "terraform-ie-project-bucket"
        key = "terraform.tfstate"
        region = "us-east-1"
        shared_credentials_file = "../.aws/credentials"
    }
}

provider "aws" {
    region = "us-east-1"
    shared_credentials_files = [ "../.aws/credentials" ]
}

module "kafka-cluster" {
    source = "./modules/kafka-cluster"
    nBroker = 3
}

output "kafka_ips" {
  value = module.kafka-cluster.publicdnslist
}