# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
terraform {
  required_version = ">= 1.0"

  backend "s3" {
    region         = "us-east-2"
    role_arn       = "arn:aws:iam::997401518295:role/NextGenAdminAccess"
    bucket         = "nextgen-aws-pxp-mf-build-terraform-state-us-east-2"
    key            = "qa-automation/testing-jobs/framework.tfstate"
    dynamodb_table = "terraform-state"
  }

  required_providers {
    aws = {
      source = "hashicorp/aws"
    }
  }
}
