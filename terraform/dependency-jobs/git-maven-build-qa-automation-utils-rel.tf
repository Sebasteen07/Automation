# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

###################################################################################
# CodeBuild project                                                               #
###################################################################################
module "qa_automation_utils_rel_codebuild" {
  source = "git::ssh://git@bitbucket.nextgen.com:7999/dope/codebuild?ref=4.3.0"

  artifact_bucket_arns = [local.pipeline_artifact_bucket_arn]
  name                 = local.qa_automation_utils_rel.name
  kms_key_id           = local.kms_key_id
  artifact_kms_key_ids = []
  artifacts_type       = var.codebuild_artifacts_type

  source_buildspec = templatefile("buildspec/buildspec.tpl", {domain = var.codeartifact_maven_domain, owner = data.aws_caller_identity.current.account_id, execution_folder = local.qa_automation_utils_rel.execution_folder, maven_command = "${local.qa_automation_utils_rel.maven_parameter} -Dmaven.test.skip=${local.qa_automation_utils_rel.maven_test_skip}"}) # var.source_buildspec
  source_type      = var.codebuild_source_type
  build_timeout    = local.qa_automation_utils_rel.build_timeout
  queued_timeout   = local.qa_automation_utils_rel.queued_timeout

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

###################################################################################
# CodePipeline job                                                                #
###################################################################################
resource "aws_codepipeline" "qa_automation_utils_rel_codepipeline" {

  name     = local.qa_automation_utils_rel.name
  role_arn = aws_iam_role.qa_automation_utils_rel.arn

  artifact_store {
    location = local.pipeline_artifact_bucket_name
    type     = "S3"
  }

  stage {
    name = "Source"

    action {
      name             = "Source"
      category         = "Source"
      owner            = "AWS"
      provider         = "CodeCommit"
      version          = "1"
      output_artifacts = ["SourceZip"]

      configuration = {
        BranchName           = local.qa_automation_utils_rel.codecommit_branch
        RepositoryName       = var.repository_name
        PollForSourceChanges = local.qa_automation_utils_rel.PollForSourceChanges
      }
    }
  }

  stage {
    name = "Build"

    action {
      name             = "CodeBuild"
      category         = "Build"
      owner            = "AWS"
      provider         = "CodeBuild"
      input_artifacts  = ["SourceZip"]
      output_artifacts = ["BuiltZip"]
      version          = "1"

      configuration = {
        ProjectName = module.qa_automation_utils_rel_codebuild.codebuild_project.name
      }
    }
  }
}

###################################################################################
# Pipeline trigger                                                                #
###################################################################################
resource "aws_cloudwatch_event_rule" "trigger_qa_automation_utils_rel_codepipeline" {
  name        = "${local.qa_automation_utils_rel.name}-trigger"
  description = "Trigger pipeline execution for ${local.qa_automation_utils_rel.name} when changes are pushed to ${local.qa_automation_utils_rel.codecommit_branch} branch in CodeCommit repo ${var.repository_name}."

  event_pattern = <<EOF
{
  "source": ["aws.codecommit"],
  "detail-type": ["CodeCommit Repository State Change"],
  "resources": ["arn:aws:codecommit:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:${var.repository_name}"],
  "detail": {
    "referenceType": ["branch"],
    "referenceName": ["${local.qa_automation_utils_rel.codecommit_branch}"],
    "event": ["referenceDeleted", "referenceCreated"]
  }
}
EOF
}

resource "aws_cloudwatch_event_target" "trigger_qa_automation_utils_rel_codepipeline" {
  rule      = aws_cloudwatch_event_rule.trigger_qa_automation_utils_rel_codepipeline.name
  target_id = "${local.qa_automation_utils_rel.name}-event-target"
  role_arn  = aws_iam_role.qa_automation_utils_rel_cwevent.arn
  arn       = aws_codepipeline.qa_automation_utils_rel_codepipeline.arn
}

###################################################################################
# IAM policies and roles                                                          #
###################################################################################

# IAM policies and roles for setting up Codepipeline triggers
resource "aws_iam_role" "qa_automation_utils_rel_cwevent" {
  name               = "${local.qa_automation_utils_rel.name}-cwevent"
  assume_role_policy = data.aws_iam_policy_document.cwevent_assume_role_policy.json
}

data "aws_iam_policy_document" "qa_automation_utils_rel_cwevent" {
  statement {
    actions = [
      "codepipeline:StartPipelineExecution",
    ]

    resources = [
      aws_codepipeline.qa_automation_utils_rel_codepipeline.arn,
    ]
  }
}

resource "aws_iam_role_policy" "qa_automation_utils_rel_cw_events_policy" {
  name   = "${local.qa_automation_utils_rel.name}-cwevents-policy"
  role   = aws_iam_role.qa_automation_utils_rel_cwevent.name
  policy = data.aws_iam_policy_document.qa_automation_utils_rel_cwevent.json
}

# IAM policies and roles for CodePipeline
resource "aws_iam_role" "qa_automation_utils_rel" {

  name               = "${local.qa_automation_utils_rel.name}-role"
  assume_role_policy = data.aws_iam_policy_document.pipeline_assume_role_policy.json
}

data "aws_iam_policy_document" "qa_automation_utils_rel_codepipeline" {

  statement {
    sid = "S3bucket"

    actions = [
      "s3:PutObject",
      "s3:GetBucketPolicy",
      "s3:GetObject",
      "s3:GetObjectVersion",
      "s3:ListBucket",
      "s3:GetBucketVersioning",
    ]

    resources = [
      "${local.pipeline_artifact_bucket_arn}",
      "${local.pipeline_artifact_bucket_arn}/*",
    ]
  }

  statement {
    sid = "kms"

    actions = [
      "kms:Decrypt",
      "kms:Encrypt",
      "kms:GenerateDataKey",
      "kms:DescribeKey",
      "kms:ReEncrypt*",
    ]

    resources = [
      local.kms_key_id,
    ]
  }

  statement {
    sid = "Builds"

    actions = [
      "codebuild:BatchGetBuilds",
      "codebuild:StartBuild",
      "codebuild:BatchGetProjects",
    ]

    resources = [
      module.qa_automation_utils_rel_codebuild.codebuild_project.arn
    ]
  }

  statement {
    sid = "Source"

    actions = [
      "codecommit:Get*",
      "codecommit:UploadArchive",
    ]

    resources = [
      data.aws_codecommit_repository.qa_automation.arn
    ]
  }
}

resource "aws_iam_role_policy" "qa_automation_utils_rel_pipeline" {

  name   = "${local.qa_automation_utils_rel.name}-pipeline-policy"
  role   = aws_iam_role.qa_automation_utils_rel.name
  policy = data.aws_iam_policy_document.qa_automation_utils_rel_codepipeline.json
}

resource "aws_iam_role_policy" "qa_automation_utils_rel_codebuild_inline_policy" {
  name   = "${local.qa_automation_utils_rel.name}-codebuild-inline-policy"
  role   = module.qa_automation_utils_rel_codebuild.codebuild_role.name
  policy = data.aws_iam_policy_document.codebuild_inline.json
}

###################################################################################
# Notifications                                                                   #
###################################################################################
resource "aws_codestarnotifications_notification_rule" "qa_automation_utils_rel" {

  detail_type = var.notification_detail_type
  event_type_ids = var.event_type_ids

  name     = "${local.qa_automation_utils_rel.name}-notification"
  resource = aws_codepipeline.qa_automation_utils_rel_codepipeline.arn

  target {
    address = var.aws_chatbot_channel_arn
    type    = "AWSChatbotSlack"
  }
}
