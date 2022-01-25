# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
locals {
  vpc_name                      = "main"
  kms_key_id                    = data.aws_ssm_parameter.kms_data_default.value
  pipeline_artifact_bucket_name = data.aws_ssm_parameter.codepipeline_artifact_store.value
  pipeline_artifact_bucket_arn  = "arn:aws:s3:::${local.pipeline_artifact_bucket_name}"
  jobname-prefix                = "qa-automation_"
  qa_automation_utils = {
      name                  = "${local.jobname-prefix}qa-automation-utils"
      codecommit_branch     = "development"
      PollForSourceChanges  = true
      pxp_application       = "Platform"
      execution_folder      = "qa-automation-utils"
      maven_test_skip       = "true"
      build_timeout         = 240     #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480     #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install"
  }

  common_steps = {
      name                  = "${local.jobname-prefix}common-steps"
      codecommit_branch     = "development"
      PollForSourceChanges  = true
      pxp_application       = "Platform"
      execution_folder      = "common-steps"
      maven_test_skip       = "true"
      build_timeout         = 240     #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480     #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install"
  }

  object_maps = {
      name                  = "${local.jobname-prefix}object-maps"
      codecommit_branch     = "development"
      PollForSourceChanges  = true
      pxp_application       = "Platform"
      execution_folder      = "object-maps"
      maven_test_skip       = "true"
      build_timeout         = 240     #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480     #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install"
  }

  integration_support_team_automation = {
      name                  = "${local.jobname-prefix}integration-support-team-automation"
      codecommit_branch     = "development"
      PollForSourceChanges  = true
      pxp_application       = "Platform"
      execution_folder      = "integration-support-team-automation"
      maven_test_skip       = "true"
      build_timeout         = 240     #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480     #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install"
  }

}
