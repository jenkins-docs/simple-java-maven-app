terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.16"
    }

  }

  required_version = ">= 1.2.0"
}

locals {
  map = {
    "HTTP" = {
      port = 80,
      cidr_blocks = ["0.0.0.0/0"],
    }
    "SSH" = {
      port = 22,
      cidr_blocks = ["213.57.121.34/32"],
    }
    "HTTPS" = {
      port = 443,
      cidr_blocks = ["0.0.0.0/0"],
    }
  }
}

resource "aws_security_group" "mysg" {
  name        = "github_actions"
  description = "Inbound Rules for guthub actions"

  dynamic "ingress" {
    for_each = local.map
    iterator = each
    content {
      description = each.key
      from_port   = each.value.port
      to_port     = each.value.port
      protocol    = "tcp"
      cidr_blocks = each.value.cidr_blocks
    }
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

}

resource "aws_instance" "example_server" {
  ami = "ami-03cc8375791cb8bcf"
  instance_type = "t2.micro"
  vpc_security_group_ids = ["${aws_security_group.mysg.id}"]
  user_data = <<-EOF
                #!/bin/bash
                sudo apt update -y
                sudo apt install docker.io -y
                sudo docker run -p 8000:8000 -d yaleea1996/simple_mav
                EOF
#   user_data = "${file("./docker_weather_app.sh")}"

  tags = {
    Name = "terraform_ec2"
  }
   
}




