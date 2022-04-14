# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
locals {
  vpc_name                      = "main"
  kms_key_id                    = data.aws_ssm_parameter.kms_data_default.value
  pipeline_artifact_bucket_name = data.aws_ssm_parameter.codepipeline_artifact_store.value
  pipeline_artifact_bucket_arn  = "arn:aws:s3:::${local.pipeline_artifact_bucket_name}"
  slack_chatbot_arn             = "arn:aws:chatbot::${data.aws_caller_identity.current.account_id}:chat-configuration/slack-channel/${var.slack_chatbot}"
  name                          = "${var.bitbucket_repository_name}_${terraform.workspace}"
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
      pxp_application       = "Portal"
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
      pxp_application       = "Portal"
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
      pxp_application       = "Portal"
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
      pxp_application       = "Portal"
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
      pxp_application       = "Portal"
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
      pxp_application       = "Portal"
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
      pxp_application       = "Portal"
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
      pxp_application       = "Portal"
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
      pxp_application       = "PatientSelfScheduling"
    }

    "prod-forms-regression1" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "forms-automation"
      test_environment      = "prod"
      suite_xml             = "forms-acceptance.xml"
      pxp_application       = "Portal"
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
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "93.0.4577.82-1"
      chrome_driver_version = "93.0.4577.63"
      cron_shedule          = "cron(30 23 ? * 6 *)"
    }

    "prod-integrations2-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pi-integration-platform-acceptance"
      test_environment      = "prod"
      suite_xml             = "integration-platform-acceptance.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "96.0.4664.45-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(20 0 ? * 1 *)"
    }

    "prod-integrations2-regression1" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pi-integration-platform-acceptance"
      test_environment      = "prod"
      suite_xml             = "integration-platform-regression.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "96.0.4664.45-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(25 0 ? * 1 *)"
    }

    "prod-integrations2-regression2" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pi-integration-platform-acceptance"
      test_environment      = "prod"
      suite_xml             = "integration-platform-regression2.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "96.0.4664.45-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(25 0 ? * 1 *)"
    }

    "prod-integrations2-regression3" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pi-integration-platform-acceptance"
      test_environment      = "prod"
      suite_xml             = "integration-platform-regression3.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "96.0.4664.45-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(25 0 ? * 1 *)"
    }

    "git-taf-prod-pss-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pss/pss2patientui-automation"
      test_environment      = "prod"
      suite_xml             = "pss-smoketests.xml"
      pxp_application       = "PatientSelfScheduling"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "93.0.4577.82-1"
      chrome_driver_version = "92.0.4515.107"
      cron_shedule          = "cron(5 1 ? * 1 *)"
    }

    "demo-integrations2-regression1" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pi-integration-platform-acceptance"
      test_environment      = "demo"
      suite_xml             = "integration-platform-regression.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "96.0.4664.45-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(0 2 ? * 4 *)"
    }

    "demo-integrations2-regression2" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pi-integration-platform-acceptance"
      test_environment      = "demo"
      suite_xml             = "integration-platform-regression2.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "96.0.4664.45-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(0 2 ? * 4 *)"
    }

    "demo-integrations2-regression3" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pi-integration-platform-acceptance"
      test_environment      = "demo"
      suite_xml             = "integration-platform-regression3.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "96.0.4664.45-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(0 2 ? * 4 *)"
    }

    "prod-integrations-acceptance-oauth10" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "integration-platform-acceptance"
      test_environment      = "prod"
      suite_xml             = "integration-platform-acceptance10.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "96.0.4664.45-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(0 22 ? * 6 *)"
    }

    "prod-integrations1-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "integration-platform-acceptance"
      test_environment      = "prod"
      suite_xml             = "integration-platform-acceptance.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "96.0.4664.45-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(0 23 ? * 6 *)"
    }

    "prod-integrations1-regression" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "integration-platform-acceptance"
      test_environment      = "prod"
      suite_xml             = "integration-platform-acceptance.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "96.0.4664.45-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(30 22 ? * 6 *)"
    }

    "demo-integrations-acceptance-oauth10" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "integration-platform-acceptance"
      test_environment      = "demo"
      suite_xml             = "integration-platform-acceptance10.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "96.0.4664.45-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(0 0 ? * 3 *)"
    }

    "demo-integrations1-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "integration-platform-acceptance"
      test_environment      = "demo"
      suite_xml             = "integration-platform-acceptance.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "96.0.4664.110-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(10 0 ? * 3 *)"
    }

    "demo-integrations1-regression" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "integration-platform-acceptance"
      test_environment      = "demo"
      suite_xml             = "integration-platform-regression.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "96.0.4664.110-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(50 0 ? * 3 *)"
    }

    "prod-practiceportal-regression1" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "practiceportal-automation"
      test_environment      = "prod"
      suite_xml             = "practice-portal-testng.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "96.0.4664.110-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(0 0 ? * 1 *)"
    }

    "git-taf-prod-p2p-directmessage" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pi-integration-platform-acceptance"
      test_environment      = "prod"
      suite_xml             = "integration-platform-acceptance-p2pDirectMessage.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "96.0.4664.110-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(15 1 ? * 1 *)"
    }

    "git-taf-prod-rcm-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "rcm-automation"
      test_environment      = "prod"
      suite_xml             = "rcm-acceptance.xml"
      pxp_application       = "Appointments"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "96.0.4664.110-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(0 2 ? * 1 *)"
    }

    "prod-practiceportal-regression2" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "practiceportal-automation"
      test_environment      = "prod"
      suite_xml             = "referrals-portal-testng.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "96.0.4664.110-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(15 0 ? * 1 *)"
    }

    "prod-sitegen-regression" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "sitegen-automation"
      test_environment      = "prod"
      suite_xml             = "sitegen-testng.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "96.0.4664.110-1"
      chrome_driver_version = "95.0.4638.17"
      cron_shedule          = "cron(30 0 ? * 1 *)"
    }

    "demo-ng-integration-pf-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "ng-integration"
      test_environment      = "demo"
      suite_xml             = "ngintegration-platform-acceptance-SmokeSuite(SinglePractice).xml"
      pxp_application       = "Platform"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "97.0.4692.71-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(0 17 * * ? *)"
    }

    "demo-ng-int-pf-acceptance-index" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "ng-integration"
      test_environment      = "demo"
      suite_xml             = "ngintegration-platform-acceptance-Inbox.xml"
      pxp_application       = "Platform"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "97.0.4692.71-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(0 14 * * ? *)"
    }

    "demo-ng-int-pf-acceptance-payment" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "ng-integration"
      test_environment      = "demo"
      suite_xml             = "ngintegration-platform-acceptance-PP139Payment.xml"
      pxp_application       = "Platform"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "97.0.4692.71-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(0 23 * * ? *)"
    }

    "demo-ng-int-pf-regression" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "ng-integration"
      test_environment      = "demo"
      suite_xml             = "ngintegration-platform-acceptance-patientEnrollment(MultiPractice).xml"
      pxp_application       = "Platform"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "97.0.4692.71-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(0 21 ? * 2 *)"
    }

    "demo-practiceportal-regression1" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "practiceportal-automation"
      test_environment      = "demo"
      suite_xml             = "practice-portal-testng.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "97.0.4692.99-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(40 1 ? * 4 *)"
    }

    "demo-practiceportal-regression2" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "practiceportal-automation"
      test_environment      = "demo"
      suite_xml             = "referrals-portal-testng.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "97.0.4692.99-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(0 3 ? * 4 *)"
    }

    "demo-sitegen-regression" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "sitegen-automation"
      test_environment      = "demo"
      suite_xml             = "sitegen-testng.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "97.0.4692.99-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(0 3 ? * 4 *)"
    }

    "demo-pss-at-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pss/pss2patientui-automation"
      test_environment      = "demo"
      suite_xml             = "pss-at-tc.xml"
      pxp_application       = "PatientSelfScheduling"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "97.0.4692.99-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(10 6 ? * 3 *)"
    }

    "demo-pss-ge-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pss/pss2patientui-automation"
      test_environment      = "demo"
      suite_xml             = "pss-ge-tc.xml"
      pxp_application       = "PatientSelfScheduling"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "97.0.4692.99-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(10 6 ? * 3 *)"
    }

    "demo-pss-geadapter-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pss/pss2patientui-automation"
      test_environment      = "demo"
      suite_xml             = "pss-ge-adpter-acceptancetest.xml"
      pxp_application       = "PatientSelfScheduling"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "97.0.4692.99-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(0 20 ? * 1-5 *)"
    }

    "demo-pss-gw-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pss/pss2patientui-automation"
      test_environment      = "demo"
      suite_xml             = "pss-gw-tc.xml"
      pxp_application       = "PatientSelfScheduling"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "97.0.4692.99-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(10 6 ? * 3 *)"
    }

    "demo-pss-ng-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pss/pss2patientui-automation"
      test_environment      = "demo"
      suite_xml             = "pss-ng-tc.xml"
      pxp_application       = "PatientSelfScheduling"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "97.0.4692.99-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(10 6 ? * 3 *)"
    }

    "demo-pss-ngadapter-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pss/pss2patientui-automation"
      test_environment      = "demo"
      suite_xml             = "pss-ng-adpter-acceptancetest.xml"
      pxp_application       = "PatientSelfScheduling"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "97.0.4692.99-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(0 20 ? * 1-5 *)"
    }

    "demo-payreporting-e2e-element" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "payreporting-automationE2E"
      test_environment      = "demo"
      suite_xml             = "payreporting-element-e2e.xml"
      pxp_application       = "Pay"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "98.0.4758.102-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(0 0 ? * 2 *)"
    }

    "demo-payreporting-e2e-paypal" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "payreporting-automationE2E"
      test_environment      = "demo"
      suite_xml             = "payreporting-paypal-e2e.xml"
      pxp_application       = "Pay"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "98.0.4758.102-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(5 0 ? * 2 *)"
    }

    "demo-payreporting-e2e-qbms" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "payreporting-automationE2E"
      test_environment      = "demo"
      suite_xml             = "payreporting-qbms-e2e.xml"
      pxp_application       = "Pay"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "98.0.4758.102-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(10 0 ? * 2 *)"
    }

    "demo-payreporting-payments" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "payreporting-automation"
      test_environment      = "demo"
      suite_xml             = "payreporting-payments-acceptance-tests.xml"
      pxp_application       = "Pay"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "98.0.4758.102-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(15 0 ? * 2 *)"
    }

    "demo-payreporting-smoke" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "payreporting-automation"
      test_environment      = "demo"
      suite_xml             = "payreporting-smoketest.xml"
      pxp_application       = "Pay"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "98.0.4758.102-1"
      chrome_driver_version = "97.0.4692.71"
      cron_shedule          = "cron(20 0 ? * * *)"
    }

    "demo-precheck" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "rcm-precheck"
      test_environment      = "demo"
      suite_xml             = "precheck.xml"
      pxp_application       = "PatientSelfScheduling"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "99.0.4844.51-1"
      chrome_driver_version = "99.0.4844.51"
      cron_shedule          = "cron(0 2 ? * 2 *)"
    }

    "demo-rcm-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "rcm-automation"
      test_environment      = "demo"
      suite_xml             = "rcm-acceptance.xml"
      pxp_application       = "Appointments"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn clean install -U"
      google_chrome_version = "99.0.4844.51-1"
      chrome_driver_version = "99.0.4844.51"
      cron_shedule          = "cron(5 23 ? * 2 *)"
    }

    "dev3-pss-api-geadapter-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pss/pss2patientui-automation"
      test_environment      = "dev3"
      suite_xml             = "pss-ge-adpter-acceptancetest.xml"
      pxp_application       = "PatientSelfScheduling"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "99.0.4844.82-1"
      chrome_driver_version = "99.0.4844.51"
      cron_shedule          = "cron(0 20 ? * 1-5 *)"
    }

    "dev3-pss-api-ngadapter-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pss/pss2patientui-automation"
      test_environment      = "dev3"
      suite_xml             = "pss-ng-adpter-acceptancetest.xml"
      pxp_application       = "PatientSelfScheduling"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "99.0.4844.82-1"
      chrome_driver_version = "99.0.4844.51"
      cron_shedule          = "cron(0 20 ? * 1-5 *)"
    }

    "dev3-pss-regression1" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pss/pss2patientui-automation"
      test_environment      = "dev3"
      suite_xml             = "pss-ng-tc.xml"
      pxp_application       = "PatientSelfScheduling"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "99.0.4844.82-1"
      chrome_driver_version = "99.0.4844.51"
      cron_shedule          = "cron(0 20 ? * 1-5 *)"
    }

    "qa1-integration-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "integration-platform-acceptance"
      test_environment      = "qa1"
      suite_xml             = "integration-platform-acceptance.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "99.0.4844.82-1"
      chrome_driver_version = "99.0.4844.51"
      cron_shedule          = "cron(0 3 ? * 1 *)"
    }

    "qa1-integration-acceptance-2_0" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "integration-platform-acceptance"
      test_environment      = "qa1"
      suite_xml             = "integration-platform-acceptance.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "99.0.4844.82-1"
      chrome_driver_version = "99.0.4844.51"
      cron_shedule          = "cron(0 3 ? * 1 *)"
    }

    "qa1-integration-acceptance-oauth10" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "integration-platform-acceptance"
      test_environment      = "qa1"
      suite_xml             = "integration-platform-acceptance10.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "99.0.4844.82-1"
      chrome_driver_version = "99.0.4844.51"
      cron_shedule          = "cron(0 3 ? * 1 *)"
    }

    "qa1-integration-regression" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "integration-platform-acceptance"
      test_environment      = "qa1"
      suite_xml             = "integration-platform-regression.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "99.0.4844.82-1"
      chrome_driver_version = "99.0.4844.51"
      cron_shedule          = "cron(0 3 ? * 1 *)"
    }

    "qa1-pi-integration-regression" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pi-integration-platform-acceptance"
      test_environment      = "qa1"
      suite_xml             = "integration-platform-acceptance.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "99.0.4844.82-1"
      chrome_driver_version = "99.0.4844.51"
      cron_shedule          = "cron(0 3 ? * 1 *)"
    }

    "qa1-practice-portal-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "practiceportal-automation"
      test_environment      = "qa1"
      suite_xml             = "practice-portal-testng.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "99.0.4844.82-1"
      chrome_driver_version = "99.0.4844.51"
      cron_shedule          = "cron(40 2 ? * 1 *)"
    }

    "qa1-sitegen-regression" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "sitegen-automation"
      test_environment      = "qa1"
      suite_xml             = "sitegen-testng.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "99.0.4844.82-1"
      chrome_driver_version = "99.0.4844.51"
      cron_shedule          = "cron(40 2 ? * 1 *)"
    }

    "dev3-integrations2-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pi-integration-platform-acceptance"
      test_environment      = "dev3"
      suite_xml             = "integration-platform-acceptance.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "100.0.4896.60-1"
      chrome_driver_version = "99.0.4844.51"
      cron_shedule          = "cron(15 20 ? * 1-5 *)"
    }

    "dev3-integrations2-regression1" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pi-integration-platform-acceptance"
      test_environment      = "dev3"
      suite_xml             = "integration-platform-regression.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "100.0.4896.60-1"
      chrome_driver_version = "100.0.4896.20"
      cron_shedule          = "cron(0 20 ? * 1-5 *)"
    }

    "dev3-integrations2-regression2" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pi-integration-platform-acceptance"
      test_environment      = "dev3"
      suite_xml             = "integration-platform-regression2.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "100.0.4896.60-1"
      chrome_driver_version = "100.0.4896.20"
      cron_shedule          = "cron(0 20 ? * 1-5 *)"
    }

    "dev3-integrations2-regression3" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "pi-integration-platform-acceptance"
      test_environment      = "dev3"
      suite_xml             = "integration-platform-regression3.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "100.0.4896.60-1"
      chrome_driver_version = "100.0.4896.20"
      cron_shedule          = "cron(0 20 ? * 1-5 *)"
    }

    "dev3-integrations-acceptance-oauth10" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "integration-platform-acceptance"
      test_environment      = "dev3"
      suite_xml             = "integration-platform-acceptance10.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "100.0.4896.88-1"
      chrome_driver_version = "100.0.4896.20"
      cron_shedule          = "cron(15 18 ? * 1-5 *)"
    }

    "dev3-integrations1-acceptance" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "integration-platform-acceptance"
      test_environment      = "dev3"
      suite_xml             = "integration-platform-acceptance.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "100.0.4896.88-1"
      chrome_driver_version = "100.0.4896.20"
      cron_shedule          = "cron(0 19 ? * 1-5 *)"
    }

    "dev3-integrations1-regression" = {
      codecommit_branch     = "development"
      PollForSourceChanges  = false
      execution_folder      = "integration-platform-acceptance"
      test_environment      = "dev3"
      suite_xml             = "integration-platform-regression.xml"
      pxp_application       = "Portal"
      build_timeout         = 240 #Number of minutes, from 5 to 480. Default value is 60 mins
      queued_timeout        = 480 #Number of minutes, from 5 to 480. Default value is 480 mins
      maven_parameter       = "mvn -U clean install"
      google_chrome_version = "100.0.4896.75-1"
      chrome_driver_version = "99.0.4844.51"
      cron_shedule          = "cron(0 20 ? * 1-5 *)"
    }
  }

  selected_test_environment      = try(local.inputs[terraform.workspace].test_environment)
  selected_suite_xml             = try(local.inputs[terraform.workspace].suite_xml)
  selected_execution_folder      = try(local.inputs[terraform.workspace].execution_folder)
  selected_maven_parameter       = try(local.inputs[terraform.workspace].maven_parameter)
  selected_google_chrome_version = try(local.inputs[terraform.workspace].google_chrome_version)
  selected_chrome_driver_version = try(local.inputs[terraform.workspace].chrome_driver_version)
}
