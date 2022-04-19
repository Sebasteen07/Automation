# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
provider "aws" {
  region = var.region
  assume_role {
    role_arn = var.role_arn
  }

  default_tags {
    tags = {
      "nextgen.automation"          = "true"
      "nextgen.component"           = var.component
      "nextgen.data-classification" = "confidential"
      "nextgen.environment"         = var.environment
      "nextgen.environment-type"    = var.environment_type
    }
  }
}