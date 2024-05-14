terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = ">= 5.5.0"
    }
    helm = {
      source  = "hashicorp/helm"
      version = ">= 2.6.0"
    }
    kubectl = {
      source  = "gavinbunney/kubectl"
      version = ">= 1.7.0"
    }
  }
  required_version = ">= 0.13"
  backend "s3" {
    bucket = "terraform-yossi-state"
    key    = "my-terraform-project"
    region = "eu-west-3"
  }
}


provider "aws" {
  region = var.region
}