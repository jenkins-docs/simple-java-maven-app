output "vpc_id" {
  value = aws_vpc.new_vpc.id
}

output "private_sub_id" {
  value = aws_subnet.private.id
}

output "public_sub_id" {
  value = aws_subnet.public.id
}