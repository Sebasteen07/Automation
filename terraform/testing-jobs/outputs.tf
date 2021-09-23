# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
output "codebuild_project_role" {
  description = "The codebuild project role"
  value       = module.build.codebuild_role
}

output "codepipeline_name" {
  description = "The codepipeline id created"
  value       = aws_codepipeline.main.id
}

output "cloudwatch_event_rule_name" {
  description = "The cloudwatch event rule name created"
  value       = aws_cloudwatch_event_rule.trigger_codepipeline.id
}
