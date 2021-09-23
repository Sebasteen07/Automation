# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

variable "topic_name" {
  description = "The friendly name for the SNS topic"
}

variable "display_name" {
  description = "The display name for the SNS topic"
}

variable "kms_master_key_id" {
  description = "The ID of an AWS-managed customer master key (CMK) for Amazon SNS or a custom CMK"
  default     = "alias/aws/sns"
}

variable "minDelayTarget" {
  description = "Minimum Delay Target"
  default     = 20
}

variable "maxDelayTarget" {
  description = "Maximum Delay Target"
  default     = 20
}

variable "numRetries" {
  description = "Number of retries"
  default     = 3
}

variable "numMaxDelayRetries" {
  description = "Max number of delay retries"
  default     = 0
}

variable "numNoDelayRetries" {
  description = "Number of no-delay retries"
  default     = 0
}

variable "numMinDelayRetries" {
  description = "Number of minimum delay retries"
  default     = 0
}

variable "backoffFunction" {
  description = "Back-off Function"
  default     = "\"linear\""
}

variable "disableSubscriptionOverrides" {
  description = "Disable Subscription Overrides?"
  default     = false
}

variable "maxReceivesPerSecond" {
  description = "Maximum receives per second"
  default     = 1
}

variable "additional_statements" {
  default     = []
  description = "Additional IAM policy statement values as a map of list items. Should be passed as [] if no additional policy is required"
}

variable "http_failure_feedback_role_arn" {
  description = "IAM role for FAILURE feedback"
}

variable "http_success_feedback_role_arn" {
  description = "IAM role for SUCCESS feedback"
}

variable "http_success_feedback_sample_rate" {
  description = "Percentage of success to sample"
}

variable "tags" {
  description = "Key-value map of resource tags"
}