variable "settings" {
  type = list(object({
    description = string
    port        = number
    cidr        = string
  }))
  default = [
    {
      description = "Allows SSH access"
      port        = 22
      cidr        = "0.0.0.0/0"
    },
    {
      description = "Allows HTTP traffic"
      port        = 80
      cidr        = "0.0.0.0/0"
    },
    {
      description = "Allows HTTPS traffic"
      port        = 443
      cidr        = "0.0.0.0/0"
    }
  ]
}

//

variable "public_subnet_cidr" {
 type        = string
 description = "Public Subnet CIDR value"
 default     = "10.0.1.0/24"
}
 
variable "private_subnet_cidr" {
 type        = string
 description = "Private Subnet CIDR value"
 default     = "10.0.2.0/24"
}

variable "availability_zone" {}