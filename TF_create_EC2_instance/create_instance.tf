provider "aws" {
    profile = "default"
    region     = "us-east-1"
}

resource "aws_instance" "terraform_server" {
    ami           = "ami-080e1f13689e07408"
    instance_type = "t2.micro"

    tags = {
        Name = "Terraform"
    }
}