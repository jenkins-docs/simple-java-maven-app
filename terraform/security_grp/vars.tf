variable "ip" {
  type = string
  default = "0.0.0.0/0"
}

variable "sec_grp_name" {
  type = string
  default = "bozo"
}

variable "vpc_id" {
  type = string
  description = "vpc_id to be given"
}

variable "instance_name" {
  type = string
  default = "noname"
}

variable "allowed_ip_list_self" {
  type = list(number)
  default = [22]
}

variable "allowed_ip_list_all" {
  type = list(number)
  default = [80,443]
}