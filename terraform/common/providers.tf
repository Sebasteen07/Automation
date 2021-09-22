# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

provider "aws" {
  region = var.region
  assume_role {
    role_arn = var.role_arn
  }
}