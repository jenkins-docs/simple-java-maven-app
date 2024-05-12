provider "aws" {
  region     = "il-central-1"
}

data "aws_availability_zones" "azs" {
  state = "available"
}
