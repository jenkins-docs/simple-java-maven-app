
locals {
    inbound_ports = [80, 443]
    outbound_ports = [0]
}


# Security Groups
resource "aws_security_group" "security_groups" {

  ingress {
        description = "ssh"
        protocol = "tcp"
        from_port = 22
        to_port = 22
        cidr_blocks = [ "0.0.0.0/0" ]
    }

    dynamic "ingress" {
        for_each = local.inbound_ports
        content {
            from_port   = ingress.value
            to_port     = ingress.value
            protocol    = "tcp"
            cidr_blocks = [ "0.0.0.0/0" ]
        }
    }

    dynamic "egress" {
        for_each = local.outbound_ports
        content {
            from_port   = egress.value
            to_port     = egress.value
            protocol    = "-1"
            cidr_blocks = ["0.0.0.0/0"]
        }
    }
}

