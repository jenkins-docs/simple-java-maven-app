module "eks" {
  source          = "terraform-aws-modules/eks/aws"
  version         = "20.8.5"
  cluster_name    = "EKS-Cluster"
  cluster_version = "1.29"


  cluster_endpoint_public_access           = true
  enable_cluster_creator_admin_permissions = true
  
  subnet_ids = module.vpc.private_subnets
  vpc_id     = module.vpc.vpc_id

  tags = {
    Name        = "app-EKS-Cluster"
    Environment = "dev"
    Terraform   = "true"
  }

  cluster_addons = {
    //aws-ebs-csi-driver = {
    //  service_account_role_arn = module.irsa-ebs-csi.iam_role_arn
    //}
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
    disk_size = 8
  }
  
  eks_managed_node_groups = {
    one = {
      name = "node-group-1"

      instance_types = ["t3.micro"]

      min_size     = 1
      max_size     = 3
      desired_size = 2
      
          labels = {
        role = "general"
      }
    }

    two = {
      name = "node-group-2"

      instance_types = ["t3.micro"]

      min_size     = 1
      max_size     = 2
      desired_size = 1
      
          labels = {
        role = "general"
      }
    }
  }
}


//data "aws_iam_policy" "ebs_csi_policy" {
//  arn = "arn:aws:iam::aws:policy/service-role/AmazonEBSCSIDriverPolicy"
//}

//module "irsa-ebs-csi" {
// source  = "terraform-aws-modules/iam/aws//modules/iam-assumable-role-with-oidc"
//  version = "5.39.0"

//  create_role                   = true
//  role_name                     = "AmazonEKSTFEBSCSIRole-${module.eks.cluster_name}"
//  provider_url                  = module.eks.oidc_provider
//  role_policy_arns              = [data.aws_iam_policy.ebs_csi_policy.arn]
//  oidc_fully_qualified_subjects = ["system:serviceaccount:kube-system:ebs-csi-controller-sa"]
//}
