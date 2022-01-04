# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
variable "cloudform_name" {
  description = "Name of the cloudformation"
  type        = string
}

variable "sns_topic_arn" {
  description = "Arn of the SNS topic created"
  type        = string
}

variable "config_name" {
  description = "The slack configuration name"
  type        = string
}

variable "slack_channel_id" {
  description = "The slack channel id to send notify"
  type        = string
}

variable "slack_channel_workspace" {
  description = "The slack channel workspace id"
  type        = string
}

variable "logging_level" {
  description = "Specifies the logging level for this configuration(ERROR, INFO, or NONE). This property affects the log entries pushed to Amazon CloudWatch Logs"
  type        = string
}

variable "role_name" {
  description = "Name of the iam role"
  type        = string
}

variable "tags" {
  description = "Additional custom tags for resources"
  type        = map(string)
  default     = {}
}

variable "assume_role_policy" {
  description = "Assume role policy"
  type        = string
}

variable "iam_role_tags" {
  description = "Tags for iam role"
  type        = map(string)
}

variable "policy_arn" {
  description = "Arn of the policy attaching to role"
  type        = string
}