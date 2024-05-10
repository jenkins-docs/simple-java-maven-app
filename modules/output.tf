output "security_groups" {
    value = aws_security_group.sg-webserver.id
    description = "The id of the security Group"
}