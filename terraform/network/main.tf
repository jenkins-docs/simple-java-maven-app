resource "aws_vpc" "new_vpc" {
  cidr_block = var.vpc_cidr # default = "10.0.0.0/16"

  tags ={
    Name = var.vpc_name
  }
}

resource "aws_subnet" "private" {
  vpc_id = aws_vpc.new_vpc.id
  cidr_block = var.private_cidr # default = "10.0.1.0/24"
  availability_zone = var.zone # default = "us-east-1a"

  tags = {
    Name = var.public_subnet_name
  }
}

resource "aws_subnet" "public" {
  vpc_id = aws_vpc.new_vpc.id
  cidr_block = var.public_cidr # default = "10.0.101.0/24"
  availability_zone = var.zone # default = "us-east-1a"
  map_public_ip_on_launch = true
  # map public ip for public subnet to enable outside traffic

  tags = {
    Name = var.public_subnet_name
  }
}
# allows communication between you VPC and the internet
resource "aws_internet_gateway" "gw" {
  vpc_id = aws_vpc.new_vpc.id
  tags = {
    Name = var.gateway_name
  }
}

resource "aws_route_table" "public" {
  vpc_id = aws_vpc.new_vpc.id
  tags = {
    Name = var.rt_name
  }
}
# Only the public subnet needs the gateway,
# we don't want to enable internet access to the private subnet
resource "aws_route" "public_internet_gateway" {
  route_table_id         = aws_route_table.public.id
  destination_cidr_block = "0.0.0.0/0"
  gateway_id             = aws_internet_gateway.gw.id
}

resource "aws_route_table_association" "public" {
  subnet_id      = aws_subnet.public.id
  route_table_id = aws_route_table.public.id
}
