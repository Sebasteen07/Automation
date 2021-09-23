# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
data "aws_vpc" "main" {
  provider = aws.pipeline

  tags = {
    Name = local.vpc_name
  }
}

data "aws_subnet_ids" "private" {
  provider = aws.pipeline

  vpc_id = data.aws_vpc.main.id
  tags = {
    type    = "private"
    subnets = "main"
  }
}

data "aws_region" "current" {
  provider = aws.pipeline
}

data "aws_caller_identity" "current" {
  provider = aws.pipeline
}

data "aws_ssm_parameter" "kms_data_default" {
  provider = aws.pipeline

  name = "/nextgen/kms/data/default"
}

data "aws_ssm_parameter" "codepipeline_artifact_store" {
  provider = aws.pipeline

  name = "/nextgen/codepipeline/artifact-store/default"
}

data "aws_security_group" "codebuild_sg" {
  provider = aws.pipeline

  name = "nextgen_default"
}

data "aws_sns_topic" "slack" {
  provider = aws.pipeline

  name = "pxp-mf-qa-dev-chatbot-sns-topic"
}

data "aws_codecommit_repository" "qa_automation" {
  provider = aws.pipeline
  
  repository_name = var.repository_name
}
