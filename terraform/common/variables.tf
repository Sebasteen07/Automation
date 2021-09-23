# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
variable "region" {
  description = "AWS region where the resurces would be created"
  default     = "us-east-2"
}

variable "environment" {
  description = "Environment name"
  default     = "dev"
}

variable "environment_type" {
  description = "Type of environment"
  default     = "test"
}

variable "role_arn" {
  description = "ARN of the IAM role that Terraform would assume in order to create the resources"
  default     = "arn:aws:iam::997401518295:role/NextGenAdminAccess"
}

variable "codecommit_user" {
  description = "IAM user used to mirror the Bitbucket repositories to CodeCommit repositories"
  default     = "codecommit_mirror"
}

variable "slack_channel_id" {
  description = "The slack channel id to send notify"
  default     = "GR0N2AYV6" #aws-chatbot-sandbox
}


variable "slack_channel_workspace" {
  description = "The slack channel workspace id"
  type        = string
  default     = "T0VUPL2KS" #nextgen
}

variable "logging_level" {
  description = "The configured slack channel logging level"
  type        = string
  default     = "INFO"
}
