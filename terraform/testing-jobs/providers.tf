# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
provider "aws" {
  region = "us-east-2"

  assume_role {
    role_arn = "arn:aws:iam::997401518295:role/NextGenAdminAccess"
  }

  default_tags {
    tags = {
      "nextgen.automation"          = true
      "nextgen.component"           = var.component
      "nextgen.data-classification" = var.data_classification
      "nextgen.environment"         = var.environment
      "nextgen.environment-type"    = var.environment-type
    }
  }
}
