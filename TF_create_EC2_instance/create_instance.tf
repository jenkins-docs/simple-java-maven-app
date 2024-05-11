provider "aws" {
    profile = "default"
    region     = "us-east-1"
}

resource "aws_instance" "maven_github_server" {
    ami           = "ami-080e1f13689e07408"
    instance_type = "t2.micro"

    tags = {
        Name = "Maven_GitHub_Actions"
    }
}