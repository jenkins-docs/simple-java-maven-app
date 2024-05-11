variable "instance_name" {
  default = "yossi"
}

variable "sec_group_id" {}

variable "subnet_id" {}

variable "ami" {
  default = "ami-00ac45f3035ff009e" # webapp ami
}

variable "user_data_script" {
  description = "User data script to be executed on the instance"
  type        = string
  default     = <<-EOF
    #!/bin/bash
    sudo apt update -y
    sudo apt install docker.io -y
    sudo systemctl start docker
    sudo systemctl enable docker
    sudo snap install amazon-ssm-agent --classic
    sudo systemctl start snap.amazon-ssm-agent.amazon-ssm-agent
    sudo systemctl enable snap.amazon-ssm-agent.amazon-ssm-agent
  EOF
}

variable "instance_profile_name" {}