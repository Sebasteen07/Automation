# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

terraform {
  required_version = ">= 1.0.0"

  backend "s3" {
    region         = "us-east-2"
    bucket         = "nextgen-aws-pxp-mf-build-terraform-state-us-east-2"
    key            = "qa-automation/common/common-resources.tfstate"
    dynamodb_table = "terraform-state"
    role_arn       = "arn:aws:iam::997401518295:role/NextGenAdminAccess"
  }
}