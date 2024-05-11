terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.16"
    }
  }
  required_version = ">= 1.2.0"
  backend "s3" {
    bucket = "terraform-yossi-state"
    key    = "my-terraform-project"
    region = "eu-west-3"
  }
}

provider "aws" {
  region = "eu-west-3"
}

module "public_instance" {
  source       = "../ec2_instance"
  sec_group_id = module.security.sec_id
  subnet_id    = module.network.public_sub_id
  instance_name = "production"
  ami = "ami-0326f9264af7e51e2" # ubuntu 22
  instance_profile_name = module.ssm_role.instance_profile_name
}

module "network" {
  source              = "../network"
  private_subnet_name = "private_yossi"
  public_subnet_name  = "public_yossi"
}

module "security" {
  source = "../security_grp"
  vpc_id = module.network.vpc_id
}

module "ssm_role" {
  source = "../ssm_role"
}