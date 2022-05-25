# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
module "build" {
  source = "git::ssh://git@bitbucket.nextgen.com:7999/dope/codebuild?ref=4.3.0"

  artifact_bucket_arns = [local.pipeline_artifact_bucket_arn]
  name                 = local.name
  kms_key_id           = local.kms_key_id
  artifact_kms_key_ids = []
  artifacts_type       = var.codebuild_artifacts_type

  environment_variables = [
    {
      name  = "maven_command"
      value = "${local.selected_maven_parameter} -Dtest.environment=${local.selected_test_environment} -Dselenium.browser=${local.selenium_browser} -Dsuite.xml=${local.selected_suite_xml} -Dexecution.mode=${local.test_execution_mode} -DencryptionKey=${local.encrypted_key}"
      type  = "PLAINTEXT"
    },
    {
      name  = "execution_folder"
      value = local.selected_execution_folder
      type  = "PLAINTEXT"
    },
    {
      name  = "google_chrome_version"
      value = local.selected_google_chrome_version
      type  = "PLAINTEXT"
    },
    {
      name  = "chrome_driver_version"
      value = local.selected_chrome_driver_version
      type  = "PLAINTEXT"
    }
  ]

  source_buildspec = var.source_buildspec
  source_type      = var.codebuild_source_type
  build_timeout    = try(local.inputs[terraform.workspace].build_timeout)
  queued_timeout   = try(local.inputs[terraform.workspace].queued_timeout)

  environment_privileged_mode             = var.codebuild_privileged_override
  environment_compute_type                = var.codebuild_compute_type
  environment_image                       = var.codebuild_image
  environment_type                        = var.codebuild_type
  environment_image_pull_credentials_type = var.codebuild_image_pull_credentials_type

  vpc_config = [{
    vpc_id             = data.aws_vpc.main.id
    subnets            = data.aws_subnet_ids.private.ids
    security_group_ids = [data.aws_security_group.codebuild_sg.id]
  }]

  common_tags = {}
}
