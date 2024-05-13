variable "docker_image_version" {
  description = "docker image version"
  type = string
}

provider "aws" {
    region = "us-east-1"
}

##################################################################################

resource "aws_instance" "maven_github_actions_server" {
  ami                    = "ami-080e1f13689e07408"
  instance_type          = "t2.micro"
  key_name               = "Prod_Keys"
  vpc_security_group_ids = ["sg-0755089add435653a"]

  user_data = <<-EOF
            #!/bin/bash
              sudo apt update
              sudo apt-get install docker.io -y
              sudo systemctl start docker
              sudo systemctl enable docker
              sudo groupadd docker
              sudo usermod -aG docker $(whoami)
              sudo docker run -d -p 80:8000 romi293/java_github_actions:${var.docker_image_version}
            EOF

  tags = {
    Name = "Maven_GitHub_Actions1"
  }
}

##################################################################################

# resource "aws_eks_cluster" "demo" {
#   name     = "my_cluster"
#   role_arn = aws_iam_role.demo.arn

#   vpc_config {
#     subnet_ids = [
#       aws_subnet.private-us-east-1a.id,
#       aws_subnet.private-us-east-1b.id,
#       aws_subnet.public-us-east-1a.id,
#       aws_subnet.public-us-east-1b.id
#     ]
#   }

#   depends_on = [aws_iam_role_policy_attachment.demo-AmazonEKSClusterPolicy]
# }







