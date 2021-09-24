# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

locals {

  # Artifact store
  artifact_store_parameter_name        = "/nextgen/codepipeline/artifact-store/default"
  artifact_store_parameter_description = "S3 bucket name for the AWS CodePipeline artifact store for QA Automation"
  codepipeline_artifact_bucket_name    = "nextgen-aws-pxp-mf-build-artifacts-us-east-2"

  # Data encryption key
  encryption_key_parameter_name        = "/nextgen/kms/data/default"
  encryption_key_parameter_description = "ARN of the data encryption KMS key for this account"
  kms_key_arn                          = "arn:aws:kms:us-east-2:754204240222:key/295a6495-f94d-407d-af72-9facc914be56"

  # CodeCommit repositories
  repository_name = {
    "qa-automation" = "qa-automation-mirror"
  }
  repository_desc = {
    "qa-automation" = "This repository mirrors the Bitbucket repository https://bitbucket.nextgen.com/projects/MFQA/repos/qa-automation/browse"
  }

  # Chatbot
  chatbot_role_name = "chatbot-monitoring-alarms-role"
  cfn_stack_name    = "chatbot-notify-slack"
  config_name       = "alarms-notify-slack-channel"
}