variable "vpc_cidr" {
  default = "10.0.0.0/16"
}
variable "public_cidr" {
  default = "10.0.101.0/24"
}
variable "private_cidr" {
  type = string
  default = "10.0.1.0/24"
}

variable "zone" {
  type = string
  description = "Applies to both subnets."
  default = "eu-west-3a"
}
variable "vpc_name" {
  type = string
  default = ""
}
variable "public_subnet_name" {
  type = string
  default = ""
}
variable "private_subnet_name" {
  type = string
  default = ""
}
variable "gateway_name" {
  type = string
  default = ""
}
variable "rt_name" {
  type = string
  default = ""
}
