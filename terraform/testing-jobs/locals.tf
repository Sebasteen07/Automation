# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
locals {
  vpc_name                      = "main"
  kms_key_id                    = data.aws_ssm_parameter.kms_data_default.value
  pipeline_artifact_bucket_name = data.aws_ssm_parameter.codepipeline_artifact_store.value
  pipeline_artifact_bucket_arn  = "arn:aws:s3:::${local.pipeline_artifact_bucket_name}"
  slack_chatbot_arn             = "arn:aws:chatbot::${data.aws_caller_identity.current.account_id}:chat-configuration/slack-channel/${var.slack_chatbot}"
  name                          = terraform.workspace
  selenium_browser              = "*chrome"
  test_execution_mode           = "headless"

  inputs = {
    "demo-integrations2-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = true
      execution_folder      = "pi-integration-platform-acceptance"
      test_environment      = "demo"
      suite_xml             = "integration-platform-acceptance.xml"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "93.0.4577.82-1"
      chrome_driver_version = "92.0.4515.107"
      cron_shedule          = "cron(10 6 ? * 3 *)"
    }

    "git-taf-prod-mu2-accessibility" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "mu2-api-automation"
      test_environment      = "prod"
      suite_xml             = "accessibility-testing.xml"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install -Dgroups=AccessibilityTests"
      google_chrome_version = "93.0.4577.82-1"
      chrome_driver_version = "92.0.4515.107"
      cron_shedule          = "cron(45 0 ? * 1 *)"
    }

    "git-taf-prod-mu2-regression" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "mu2-api-automation"
      test_environment      = "prod"
      suite_xml             = "mu2-api-automation.xml"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U -Dgroups=RegressionTests"
      google_chrome_version = "93.0.4577.82-1"
      chrome_driver_version = "92.0.4515.107"
      cron_shedule          = "cron(0 1 ? * 1 *)"
    }

    "git-taf-prod-mu2-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "mu2-api-automation"
      test_environment      = "prod"
      suite_xml             = "mu2-api-automation.xml"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U -Dgroups=AcceptanceTests"
      google_chrome_version = "93.0.4577.82-1"
      chrome_driver_version = "92.0.4515.107"
      cron_shedule          = "cron(0 0 ? * 1 *)"
    }

    "prod-patientportal-regression1" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "jalapeno-automation"
      test_environment      = "prod"
      suite_xml             = "patientportal2-acceptance-basics.xml"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "93.0.4577.82-1"
      chrome_driver_version = "92.0.4515.107"
      cron_shedule          = "cron(0 23 ? * 6 *)"
    }

    "prod-patientportal-regression2" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "jalapeno-automation"
      test_environment      = "prod"
      suite_xml             = "patientportal2-acceptance-solutions.xml"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "93.0.4577.82-1"
      chrome_driver_version = "92.0.4515.107"
      cron_shedule          = "cron(0 0 ? * 6 *)"
    }

    "prod-patientportal-regression3" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "jalapeno-automation"
      test_environment      = "prod"
      suite_xml             = "patientportal2-acceptance-linkedaccounts.xml"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "93.0.4577.82-1"
      chrome_driver_version = "92.0.4515.107"
      cron_shedule          = "cron(30 23 ? * 6 *)"
    }

    "prod-patientportal-regression4" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "jalapeno-automation"
      test_environment      = "prod"
      suite_xml             = "patientportal2-acceptance-MU3.xml"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "93.0.4577.82-1"
      chrome_driver_version = "92.0.4515.107"
      cron_shedule          = "cron(45 23 ? * 6 *)"
    }

    "git-taf-prod-precheck" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "rcm-precheck"
      test_environment      = "prod"
      suite_xml             = "precheck.xml"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "93.0.4577.82-1"
      chrome_driver_version = "92.0.4515.107"
      cron_shedule          = "cron(0 1 ? * 1 *)"
    }

    "git-taf-prod-precheck-rsdk-patientupdates" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pi-integration-platform-acceptance"
      test_environment      = "prod"
      suite_xml             = "integration-platform-regression-preCheckAppointment.xml"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "93.0.4577.82-1"
      chrome_driver_version = "92.0.4515.107"
      cron_shedule          = "cron(45 0 ? * 1 *)"
    }

    "prod-forms-regression1" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "forms-automation"
      test_environment      = "prod"
      suite_xml             = "forms-acceptance.xml"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "93.0.4577.82-1"
      chrome_driver_version = "92.0.4515.107"
      cron_shedule          = "cron(0 23 ? * 6 *)"
    }

    "prod-forms-regression2" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "forms-automation"
      test_environment      = "prod"
      suite_xml             = "forms-calculated-acceptance.xml"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "93.0.4577.82-1"
      chrome_driver_version = local.qa_chrome_driver_version
      cron_shedule          = "cron(30 23 ? * 6 *)"
    }
  }

  selected_test_environment      = try(local.inputs[local.name].test_environment)
  selected_suite_xml             = try(local.inputs[local.name].suite_xml)
  selected_execution_folder      = try(local.inputs[local.name].execution_folder)
  selected_maven_parameter       = try(local.inputs[local.name].maven_parameter)
  selected_google_chrome_version = try(local.inputs[local.name].google_chrome_version)
  selected_chrome_driver_version = try(local.inputs[local.name].chrome_driver_version)
}
