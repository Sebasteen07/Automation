# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
data "aws_vpc" "main" {

  tags = {
    Name = local.vpc_name
  }
}

data "aws_subnets" "private" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.main.id]
  }

  tags = {
    type    = "private"
    subnets = "main"
  }
}


data "aws_region" "current" {
}

data "aws_caller_identity" "current" {
}

data "aws_ssm_parameter" "kms_data_default" {

  name = "/nextgen/kms/data/default"
}

data "aws_ssm_parameter" "codepipeline_artifact_store" {

  name = "/nextgen/codepipeline/artifact-store/default"
}

data "aws_security_group" "codebuild_sg" {

  name = "nextgen_default"
}

data "aws_codecommit_repository" "qa_automation" {

  repository_name = var.repository_name
}
