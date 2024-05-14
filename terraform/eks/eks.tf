module "eks" {
  source  = "terraform-aws-modules/eks/aws"
  version = "~> 19.0"

  cluster_name    = var.cluster_name
  cluster_version = "1.27"

  cluster_endpoint_private_access = var.cluster_endpoint_private_access
  cluster_endpoint_public_access  = var.cluster_endpoint_public_access

  vpc_id     = module.vpc.vpc_id
  subnet_ids = module.vpc.private_subnets

  enable_irsa = var.enable_irsa


  cluster_addons = {
    coredns = {
      most_recent = true
    }
    kube-proxy = {
      most_recent = true
    }
    vpc-cni = {
      most_recent = true
    }
  }

  eks_managed_node_group_defaults = {
    disk_size = 30
  }

  eks_managed_node_groups = {
    general = {
      desired_size = 1
      min_size     = 1
      max_size     = 10

      labels = {
        role = "general"
      }

      instance_types = ["t3.micro"]
      capacity_type  = "ON_DEMAND"
    }

    spot = {
      desired_size = 1
      min_size     = 1
      max_size     = 10

      labels = {
        role = "dev"
      }

      #   taints = [{
      #     key    = "dedicated"
      #     value  = "spot"
      #     effect = "NO_SCHEDULE"
      #   }]
      taints = {
        dedicated = {
          key    = "dedicated"
          value  = "spot"
          effect = "NO_SCHEDULE"
        }
      }
      instance_types = ["t3.micro"]
      capacity_type  = "SPOT"
    }
  }
  # manage_aws_auth_configmap = true
  # aws_auth_roles = [
  #   {
  #     rolearn  = module.eks_terraform_admin_iam_assumable_role.iam_role_arn
  #     username = module.eks_terraform_admin_iam_assumable_role.iam_role_name
  #     groups   = ["system:masters"]
  #   },
  # ]

  tags = var.eks_tags
}

# Commented stuff is for rbac
# IAM roles and policies that allow access to k8s workloads
#


# data "aws_eks_cluster" "cluster" {
#   name = module.eks.cluster_name
# }

# data "aws_eks_cluster_auth" "cluster" {
#   name = module.eks.cluster_name
# }

# provider "kubernetes" {
#   host                   = data.aws_eks_cluster.cluster.endpoint
#   cluster_ca_certificate = base64decode(data.aws_eks_cluster.cluster.certificate_authority.0.data)
#   token                  = data.aws_eks_cluster_auth.cluster.token
#   exec {
#     api_version = "client.authentication.k8s.io/v1beta1"
#     args        = ["eks", "get-token", "--cluster-name", data.aws_eks_cluster.cluster.id]
#     command     = "aws"
#   }
# }