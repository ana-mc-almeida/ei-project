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

variable "nBroker" {
  description = "number of brokers"
  type        = number
  default     = 3
}

variable "db_username" {
  description = "The username for the database"
  type        = string
  sensitive   = true
  default     = "ie_project"
}

variable "db_password" {
  description = "The password for the database"
  type        = string
  sensitive   = true
  default     = "password"
}

variable "db_name" {
  description = "The name to use for the database"
  type        = string
  default     = "laas"
}

module "kafka-cluster" {
    source = "./modules/kafka-cluster"
    nBroker = var.nBroker
}

module "rds" {
    source = "./modules/rds"
    db_username = var.db_username
    db_password = var.db_password
    db_name = var.db_name
}

module "ollama" {
    source = "./modules/ollama"
}

output "kafka_ips" {
  value = module.kafka-cluster.publicdnslist
}

output "rds_address" {
  value = module.rds.address
}

output "rds_port" {
  value = module.rds.port
}

output "ollama_address" {
  value = module.ollama.address
}