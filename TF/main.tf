provider "aws" {
    region = "us-east-1"
}

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
              sudo usermod -aG docker ubuntu
              sudo docker run --name weather_app_tf -p 80:8080 -d romi293/java_github_actions:latest
              EOF

  tags = {
    Name = "Maven_GitHub_Actions"
  }
}


