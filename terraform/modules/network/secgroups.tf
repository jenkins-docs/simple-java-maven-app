resource "aws_security_group" "my_sg" {
  name        = "my-tf-sg"

  dynamic "ingress" {
    for_each = var.settings
    iterator = sg_ingress

    content {
      description = sg_ingress.value["description"]
      from_port   = sg_ingress.value["port"]
      to_port     = sg_ingress.value["port"]
      protocol    = "tcp"
      cidr_blocks = [sg_ingress.value["cidr"]]
    }
  }
  
   egress {
         from_port   = 0
         to_port     = 0
         protocol    = "-1"
         cidr_blocks = ["0.0.0.0/0"]
       }

  tags = {
    Name = "my_tf_sg_ex4"
  }
}
