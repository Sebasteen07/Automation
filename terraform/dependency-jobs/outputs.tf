# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

# Outputs for git-maven-build-qa-automation-utils-rel:
# -----------------------------------------------------
output "qa_automation_utils_codebuild_project_role" {
  description = "The codebuild project role for git-maven-build-qa-automation-utils-rel"
  value       = module.qa_automation_utils_codebuild.codebuild_role
}

output "qa_automation_utils_codepipeline_name" {
  description = "The codepipeline id created for test execution of git-maven-build-qa-automation-utils-rel"
  value       = aws_codepipeline.qa_automation_utils_codepipeline.id
}

output "qa_automation_utils_cloudwatch_event_rule_name" {
  description = "Cloudwatch event rule name created for triggering pipeline execution of git-maven-build-qa-automation-utils-rel"
  value       = aws_cloudwatch_event_rule.trigger_qa_automation_utils_codepipeline.id
}
