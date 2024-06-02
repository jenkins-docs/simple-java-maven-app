terraform {

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.16"
    }
  }
    
    backend "s3" {
    bucket         	   = "denis-tf-bucket"
    key              	   = "state/terraform.tfstate"
    region         	   = "eu-central-1"
    encrypt        	   = true
    dynamodb_table = "mycomponents_tf_lockid"
  }
}

provider "aws" {
  region  = "eu-central-1"
  
}

module "securitygroup" {
  source  = "Shossi/securitygroup/aws"
  version = "1.5.5"
  allowed_ip_list_all = [8080]
}

resource "aws_instance" "ec2_instance" {
	ami = "ami-026c3177c9bd54288"
	instance_type = "t2.micro"
	key_name = "web_app"
	
	tags = {
		Name = "Simple-app"
	}
	
	vpc_security_group_ids = [module.securitygroup.sec_id]
    
    user_data = <<-EOF
          #!/bin/bash
            sudo apt-get update
            sudo apt-get install docker.io -y
            sudo systemctl start docker
            sudo systemctl enable docker
            sudo usermod -a -G docker $(whoami)
            newgrp docker
            sudo docker stop simple-app
            sudo docker system prune -a
            sudo docker run -d --name simple-app -p 8080:8080 denisiuss/simple-java-maven-app:latest
          EOF
	
}


