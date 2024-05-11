resource "aws_instance" "example_server" {
  ami                    = var.ami # default ami = ami-0a774423676be7e50 for web_app - configured ami.
  instance_type          = "t2.micro"
  vpc_security_group_ids = [var.sec_group_id]
  subnet_id = var.subnet_id
  user_data = var.user_data_script # default installs docker.
  iam_instance_profile = var.instance_profile_name
  tags = {
    Name = var.instance_name
  }
}

