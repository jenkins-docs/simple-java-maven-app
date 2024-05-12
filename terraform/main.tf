terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
  backend "s3" {
    bucket                  = "ziv-tf-state"
    key                     = "my-terraform-project"
    region                  = "us-east-1"
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
    for pkg in docker.io docker-doc docker-compose docker-compose-v2 podman-docker containerd runc; do sudo apt-get remove $pkg; done
    sudo apt-get update -y
    sudo apt-get install ca-certificates curl
    sudo install -m 0755 -d /etc/apt/keyrings
    sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
    sudo chmod a+r /etc/apt/keyrings/docker.asc
    echo \
      "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
      $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
      sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
    sudo apt-get update
    sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin -y
  
    # Run the Docker image
    sudo docker run -d -p 8000:8000 zivgl66/ziv-repo:simple-maven-1.0.0
  EOF

}
