# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
provider "aws" {
  region = "us-east-2"

  assume_role {
    role_arn = "arn:aws:iam::997401518295:role/NextGenAdminAccess"
  }
}
