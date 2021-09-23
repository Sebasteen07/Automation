# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

output "qa_automation_codecommit_repo_name" {
  description = "AWS CodeCommit repo to which the Bitbucket repo qa-automation would mirror its content"
  value       = aws_codecommit_repository.qa_automation_repo.id
}

output "codepipeline_artifact_bucket_parameter_name" {
  description = "S3 bucket name used for the CodePipeline jobs"
  value       = aws_ssm_parameter.codepipeline_artifact_bucket_name.name
}

output "data_encryption_key_parameter_name" {
  description = "SSM parameter name for storing the value of data encryption KMS key for this account"
  value       = aws_ssm_parameter.kms_key_arn.name
}

output "sns_topic_arn" {
  description = "The SNS topic created by this module"
  value       = module.sns_module.sns_topic.arn
}

output "chatbot_iam_role_arn" {
  description = "IAM role assumed by the AWS Chatbot channel"
  value       = module.chatbot_module.iam_role.arn
}