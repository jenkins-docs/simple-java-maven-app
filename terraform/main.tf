terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.16"
    }
  }

  required_version = ">= 1.2.0"
}

provider "aws" {
  region = "us-east-1" 
}

module "security" {
  source = "./modules/"
}

resource "aws_instance" "instance" {
  ami           = "ami-04b70fa74e45c3917" 
  instance_type = "t2.micro" 
  vpc_security_group_ids = [module.security.security_groups]
  key_name = "weather_app_key"


  tags = {
    Name = "maven-app" 
  }

  user_data = <<-EOF
  #!/bin/bash
  # Install Docker
  sudo apt-get update -y
  sudo apt-get install -y docker
  sudo systemctl start docker

  # Run the Docker image
  sudo docker run -d -p 8000:8000 zivgl66/ziv-repo:simple-maven-1.0.0
  EOF

}