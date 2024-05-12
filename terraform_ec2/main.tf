terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
    }
  }
}

provider "aws" {
  region = var.region
}

module "ec2_network" {
  source            = "./modules/network"
  availability_zone = var.availability_zone
}

resource "aws_instance" "my_ubuntu" {
  ami                    = var.ami_id
  instance_type          = var.type_of_instance
  key_name               = var.ssh_key
  availability_zone      = var.availability_zone
  vpc_security_group_ids = [module.ec2_network.security_group_id]
  root_block_device {
    delete_on_termination = true
    encrypted             = false
    volume_size           = var.volume_size
    volume_type           = var.volume_type
  }
  user_data = <<-EOF
              #!/bin/bash
              sudo apt-get update > /var/log/user_data.log 2>&1 
              sudo apt-get install docker.io -y
              EOF

  tags = {
    Name = var.instance_name
  }
}


