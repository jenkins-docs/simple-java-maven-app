provider "aws" {
    region     = "us-east-1"
}

resource "aws_instance" "maven_github_server" {
    ami           = "ami-080e1f13689e07408"
    instance_type = "t2.micro"
    security_groups = [ "sg-0755089add435653a" ]

    tags = {
        Name = "Maven_GitHub_Actions1"
    }
}

