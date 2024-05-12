resource "aws_vpc" "tf_vpc" {
 cidr_block = "10.0.0.0/16"
 
 tags = {
   Name = "my_vpc"
 }
}

resource "aws_subnet" "public_subnet" {
 vpc_id     = aws_vpc.tf_vpc.id
 cidr_block = var.public_subnet_cidr
 availability_zone = var.availability_zone

 
 tags = {
   Name = "Public Subnet TF"
 }
}
 
resource "aws_subnet" "private_subnet" {
 vpc_id     = aws_vpc.tf_vpc.id
 cidr_block = var.private_subnet_cidr
 availability_zone = var.availability_zone
 
 tags = {
   Name = "Private Subnet TF"
 }
}

resource "aws_internet_gateway" "tf_gw" {
 vpc_id = aws_vpc.tf_vpc.id
 
 tags = {
   Name = "TF VPC IG"
 }
}

resource "aws_route_table" "tf_routetable" {
 vpc_id = aws_vpc.tf_vpc.id
 
 route {
   cidr_block = "0.0.0.0/0"
   gateway_id = aws_internet_gateway.tf_gw.id
 }
 
 tags = {
   Name = "TF Route Table"
 }
}

resource "aws_route_table_association" "public_subnet_asso" {
 subnet_id      = aws_subnet.public_subnet.id
 route_table_id = aws_route_table.tf_routetable.id
}