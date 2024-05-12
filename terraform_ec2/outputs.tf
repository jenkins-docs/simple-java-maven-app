output "ec2_public_ip" {
  description = "Public IP of the EC2 instance"
  value       = ["${aws_instance.my_ubuntu.public_ip}"]
}

output "ec2_private_ip" {
  description = "Private IP of the EC2 instance"
  value       = ["${aws_instance.my_ubuntu.private_ip}"]
}

output "network_interface_id" {
  value = aws_instance.my_ubuntu.primary_network_interface_id
}

output "instance_name" {
  description = "Name of the EC2 instance"
  value       = aws_instance.my_ubuntu.tags.Name
}