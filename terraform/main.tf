variable "private_key_path" {
  description = "Path to the SSH private key"
}

terraform {

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.16"
    }
  }
}

provider "aws" {
  region  = "eu-central-1"
  profile = "default"
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
		Name = "Docker-inst"
	}
	
	vpc_security_group_ids = [module.securitygroup.sec_id]
	
	provisioner "remote-exec" {
      inline = [
        "sudo apt update",
        "sudo apt install -y docker.io",
        "sudo systemctl start docker",
        "sudo docker stop simple-app || true",
        "sudo docker system prune -af",
        "sudo docker run -d --name simple-app -p 8080:8080 denisiuss/simple-java-maven-app:latest"
      ]
    }  
      
    connection {
		type        = "ssh"
		user        = "ubuntu"  
		private_key = var.private_key_path
		host        = aws_instance.ec2_instance.public_ip
	}	
}
