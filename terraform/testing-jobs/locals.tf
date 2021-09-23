# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
locals {
  vpc_name                      = "main"
  kms_key_id                    = data.aws_ssm_parameter.kms_data_default.value
  aws_codebuild_project_arn     = "arn:aws:codebuild:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:project/${module.build.codebuild_project.name}"
  pipeline_artifact_bucket_name = data.aws_ssm_parameter.codepipeline_artifact_store.value
  pipeline_artifact_bucket_arn  = "arn:aws:s3:::${local.pipeline_artifact_bucket_name}"
  name                          = terraform.workspace
  aws_codecommit_repo_arn       = "arn:aws:codecommit:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:${var.repository_name}"
  selenium_browser              = "*chrome"
  test_execution_mode           = "headless"

  inputs = {
    "default" = {
      codecommit_branch     = "master"
      PollForSourceChanges  = true
      execution_folder      = "pi-integration-platform-acceptance"
      test_environment      = "demo"
      suite_xml             = "codebuild.xml"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "93.0.4577.82-1"
      chrome_driver_version = "92.0.4515.107"
      cron_shedule          = "cron(0 10 * * ? *)"
    }
  }

  selected_test_environment      = try(local.inputs[local.name].test_environment)
  selected_suite_xml             = try(local.inputs[local.name].suite_xml)
  selected_execution_folder      = try(local.inputs[local.name].execution_folder)
  selected_maven_parameter       = try(local.inputs[local.name].maven_parameter)
  selected_google_chrome_version = try(local.inputs[local.name].google_chrome_version)
  selected_chrome_driver_version = try(local.inputs[local.name].chrome_driver_version)

  common_tags = {
    "nextgen.automation"          = true
    "nextgen.component"           = var.component
    "nextgen.data-classification" = var.data_classification
    "nextgen.environment"         = var.environment
    "nextgen.environment-type"    = var.environment-type
  }
}
