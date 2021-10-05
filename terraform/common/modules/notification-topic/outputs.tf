# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

output "sns_topic" {
  value       = aws_sns_topic.this
  description = "This SNS topic"
}