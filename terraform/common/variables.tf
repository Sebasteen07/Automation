# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
variable "region" {
  description = "AWS region where the resurces would be created"
  default     = "us-east-2"
}

variable "environment" {
  description = "Environment name"
  default = "dev"
}

variable "environment_type" {
  description = "Type of environment"
  default = "test"
}

variable "role_arn" {
  description = "ARN of the IAM role that Terraform would assume in order to create the resources"
  default     = "arn:aws:iam::997401518295:role/NextGenAdminAccess"
}

variable "base_tags" {
  description = "Map of tags added to all AWS resources."
  type        = map(any)
  default = {
    "nextgen.automation"          = "true"
    "nextgen.component"           = "PXP"
    "nextgen.data-classification" = "confidential"
  }
}
