output "security_groups" {
    value = aws_security_group.security_groups.id
    description = "The id of the security Group"
}
