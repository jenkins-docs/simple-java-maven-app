variable "region" {
  type        = string
  default = "il-central-1"
  description = "The aws ssh key to use with the server."
}

variable "type_of_instance" {
  type        = string
  default     = "t3.micro"
  description = "The type of instance to use for the server."
}

variable "ami_id" {
  type        = string
  default     = "ami-04a4b28d712600827"
  description = "The id of the machine image (AMI) to use for the server."

  validation {
    condition     = length(var.ami_id) > 4 && substr(var.ami_id, 0, 4) == "ami-"
    error_message = "The image_id value must be a valid AMI id, starting with \"ami-\"."
  }
}

variable "ssh_key" {
  type        = string
  default     = "Linux01_il"
  description = "The aws ssh key to use with the server."
}

variable "instance_name" {
  type        = string
  default     = "Infinity Terraform Test Server"
  description = "The name to assign for the server."
}

variable "volume_size" {
  type        = number
  default     = 8
  description = "The size of volume for the server."
}

variable "volume_type" {
  type        = string
  default     = "gp2"
  description = "The type of volume for the server."
}

variable "availability_zone" {
  type        = string
  default     = "il-central-1a"
  description = "The AZ for the server."
}

variable "dhub_pass" {
  type        = string
}

variable "dhub_user" {
  type        = string
}


