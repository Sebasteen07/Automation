# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
provider "aws" {
  region = "us-east-2"

  assume_role {
    role_arn = "arn:aws:iam::997401518295:role/NextGenAdminAccess"
  }

  default_tags {
    tags = {
      "Name"                        = local.name
      "nextgen.automation"          = true
      "nextgen.component"           = var.component
      "nextgen.data-classification" = var.data_classification
      "nextgen.environment"         = var.environment
      "nextgen.environment-type"    = var.environment-type
      "pxp.application"             = try(local.inputs[terraform.workspace].pxp_application)
      "pxp.component"               = var.bitbucket_repository_name
    }
  }
}
