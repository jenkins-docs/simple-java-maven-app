variable "region" {
  default = "il-central-1"
}

data "aws_availability_zones" "available" {}

locals {
  cluster_name = "EKS-Cluster"
}
module "vpc" {
  //using terraform vpc module
  //https://registry.terraform.io/modules/terraform-aws-modules/vpc/aws/latest
  source = "terraform-aws-modules/vpc/aws"
  //latest version (10/05/2024)
  version              = "5.8.1"
  name                 = "app-VPC"
  cidr                 = "192.168.0.0/16"
  azs                  = data.aws_availability_zones.available.names
  //for internet facing services:
  public_subnets       = ["192.168.1.0/24", "192.168.2.0/24"]
  //for db's, backends:
  private_subnets      = ["192.168.3.0/24", "192.168.4.0/24"]
  enable_nat_gateway   = true
  single_nat_gateway   = true
  //will create several nat gateways - each in every az
  one_nat_gateway_per_az = false
  enable_dns_hostnames = true
  enable_dns_support   = true

  tags = {
    "Name"      = "app-VPC"
    Terraform   = "true"
    Environment = "dev"
  }
  public_subnet_tags = {
    "Name"                   = "app-Public-Subnet"
    "kubernetes.io/role/elb" = 1
  }
  private_subnet_tags = {
    "Name"                            = "app-Private-Subnet"
    "kubernetes.io/role/internal-elb" = 1
  }

  //create elastic ip for public subnets
  //resource "aws_eip" "nat" {
  //  count = 1
  //
  //  vpc = true
  //}
}
