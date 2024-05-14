#### VPC ###
vpc_name = "eks-terraform-vpc"
enable_nat_gateway = true
enable_vpn_gateway = true


### Cluster ##
cluster_name = "eks-terraform-project"
cluster_endpoint_private_access = true
cluster_endpoint_public_access  = true