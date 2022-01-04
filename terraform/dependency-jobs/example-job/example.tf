# Copyright 2021 NXGN Management, LLC. All Rights Reserved.

###################################################################################
# CodeBuild project                                                               #
###################################################################################
module "dependency_jobname_codebuild" {
  source = "git::ssh://git@bitbucket.nextgen.com:7999/dope/codebuild?ref=4.3.0"

  artifact_bucket_arns = [local.pipeline_artifact_bucket_arn]
  name                 = local.dependency_jobname.name
  kms_key_id           = local.kms_key_id
  artifact_kms_key_ids = []
  artifacts_type       = var.codebuild_artifacts_type

  source_buildspec = templatefile("templates/buildspec.tpl", {domain = var.codeartifact_maven_domain, owner = data.aws_caller_identity.current.account_id, execution_folder = local.dependency_jobname.execution_folder, maven_command = "${local.dependency_jobname.maven_parameter} -Dmaven.test.skip=${local.dependency_jobname.maven_test_skip}"}) # var.source_buildspec
  source_type      = var.codebuild_source_type
  build_timeout    = local.dependency_jobname.build_timeout
  queued_timeout   = local.dependency_jobname.queued_timeout

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
resource "aws_codepipeline" "dependency_jobname_codepipeline" {

  name     = local.dependency_jobname.name
  role_arn = aws_iam_role.dependency_jobname.arn

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
        BranchName           = local.dependency_jobname.codecommit_branch
        RepositoryName       = var.repository_name
        PollForSourceChanges = local.dependency_jobname.PollForSourceChanges
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
        ProjectName = module.dependency_jobname_codebuild.codebuild_project.name
      }
    }
  }
}

###################################################################################
# Pipeline trigger                                                                #
###################################################################################
resource "aws_cloudwatch_event_rule" "trigger_dependency_jobname_codepipeline" {
  name        = "${local.dependency_jobname.name}-trigger"
  description = "Trigger pipeline execution for ${local.dependency_jobname.name} when changes are pushed to ${local.dependency_jobname.codecommit_branch} branch in CodeCommit repo ${var.repository_name}."

  event_pattern = <<EOF
{
  "source": ["aws.codecommit"],
  "detail-type": ["CodeCommit Repository State Change"],
  "resources": ["arn:aws:codecommit:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:${var.repository_name}"],
  "detail": {
    "referenceType": ["branch"],
    "referenceName": ["${local.dependency_jobname.codecommit_branch}"],
    "event": ["referenceDeleted", "referenceCreated"]
  }
}
EOF
}

resource "aws_cloudwatch_event_target" "trigger_dependency_jobname_codepipeline" {
  rule      = aws_cloudwatch_event_rule.trigger_dependency_jobname_codepipeline.name
  target_id = "${local.dependency_jobname.name}-event-target"
  role_arn  = aws_iam_role.dependency_jobname_cwevent.arn
  arn       = aws_codepipeline.dependency_jobname_codepipeline.arn
}

###################################################################################
# IAM policies and roles                                                          #
###################################################################################

# IAM policies and roles for setting up Codepipeline triggers
resource "aws_iam_role" "dependency_jobname_cwevent" {
  name               = "${local.dependency_jobname.name}-cwevent"
  assume_role_policy = data.aws_iam_policy_document.cwevent_assume_role_policy.json
}

data "aws_iam_policy_document" "dependency_jobname_cwevent" {
  statement {
    actions = [
      "codepipeline:StartPipelineExecution",
    ]

    resources = [
      aws_codepipeline.dependency_jobname_codepipeline.arn,
    ]
  }
}

resource "aws_iam_role_policy" "dependency_jobname_cw_events_policy" {
  name   = "${local.dependency_jobname.name}-cwevents-policy"
  role   = aws_iam_role.dependency_jobname_cwevent.name
  policy = data.aws_iam_policy_document.dependency_jobname_cwevent.json
}

# IAM policies and roles for CodePipeline
resource "aws_iam_role" "dependency_jobname" {

  name               = "${local.dependency_jobname.name}-role"
  assume_role_policy = data.aws_iam_policy_document.pipeline_assume_role_policy.json
}

data "aws_iam_policy_document" "dependency_jobname_codepipeline" {

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
      module.dependency_jobname_codebuild.codebuild_project.arn
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

resource "aws_iam_role_policy" "dependency_jobname_pipeline" {

  name   = "${local.dependency_jobname.name}-pipeline-policy"
  role   = aws_iam_role.dependency_jobname.name
  policy = data.aws_iam_policy_document.dependency_jobname_codepipeline.json
}

resource "aws_iam_role_policy" "dependency_jobname_codebuild_inline_policy" {
  name   = "${local.dependency_jobname.name}-codebuild-inline-policy"
  role   = module.dependency_jobname_codebuild.codebuild_role.name
  policy = data.aws_iam_policy_document.codebuild_inline.json
}

###################################################################################
# Notifications                                                                   #
###################################################################################
resource "aws_codestarnotifications_notification_rule" "dependency_jobname" {

  detail_type = var.notification_detail_type
  event_type_ids = var.event_type_ids

  name     = "${local.dependency_jobname.name}-notification"
  resource = aws_codepipeline.dependency_jobname_codepipeline.arn

  target {
    address = var.aws_chatbot_channel_arn
    type    = "AWSChatbotSlack"
  }
}
