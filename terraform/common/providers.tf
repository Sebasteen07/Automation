# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

provider "aws" {
  region = var.region
  assume_role {
    role_arn = var.role_arn
  }

  default_tags {
    tags = {
      "nextgen.automation"          = "true"
      "nextgen.component"           = "PXP"
      "nextgen.data-classification" = "confidential"
    }
  }
}