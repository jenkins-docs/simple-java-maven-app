resource "aws_security_group" "public" {
  name = "name"
  description = "Allow inbound web traffic"
  vpc_id      = var.vpc_id
  dynamic "ingress" {
    for_each = var.allowed_ip_list_all
    content {
      from_port = ingress.value
      to_port = ingress.value
      protocol = "tcp"
      cidr_blocks = ["0.0.0.0/0"]
    }
  }
  dynamic "ingress" {
    for_each = var.allowed_ip_list_self
    content {
      from_port = ingress.value
      to_port = ingress.value
      protocol = "tcp"
      cidr_blocks = [var.ip]
    }
  }

  egress  { # Connection from the machine
    cidr_blocks = [ "0.0.0.0/0" ]
    description = "All networks allowed"
    from_port = 0
    to_port = 0
    protocol = "-1"
  }

  tags = {
    "Name" = var.sec_grp_name # default = "bozo"
  }
}