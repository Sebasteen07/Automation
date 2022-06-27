# Copyright 2022 NXGN Management, LLC. All Rights Reserved.

###################################################################################
# CodeBuild project                                                               #
###################################################################################
module "payreporting_service_codebuild" {
  source = "git::ssh://git@bitbucket.nextgen.com:7999/dope/codebuild?ref=4.3.0"

  artifact_bucket_arns = [local.pipeline_artifact_bucket_arn]
  name                 = local.payreporting_service.name
  kms_key_id           = local.kms_key_id
  artifact_kms_key_ids = []
  artifacts_type       = var.codebuild_artifacts_type

  environment_variables = [
    {
      name  = "maven_command"
      value = "${local.payreporting_service.maven_parameter} -Dmaven.test.skip=${local.payreporting_service.maven_test_skip}"
      type  = "PLAINTEXT"
    },
    {
      name  = "execution_folder"
      value = local.payreporting_service.execution_folder
      type  = "PLAINTEXT"
    },
    {
      name  = "domain"
      value = var.codeartifact_maven_domain
      type  = "PLAINTEXT"
    },
    {
      name  = "owner"
      value = data.aws_caller_identity.current.account_id
      type  = "PLAINTEXT"
    }
  ]

  source_buildspec = var.source_buildspec
  source_type      = var.codebuild_source_type
  build_timeout    = local.payreporting_service.build_timeout
  queued_timeout   = local.payreporting_service.queued_timeout

  environment_privileged_mode             = var.codebuild_privileged_override
  environment_compute_type                = var.codebuild_compute_type
  environment_image                       = var.codebuild_image
  environment_type                        = var.codebuild_type
  environment_image_pull_credentials_type = var.codebuild_image_pull_credentials_type

  vpc_config = [{
    vpc_id             = data.aws_vpc.main.id
    subnets            = data.aws_subnets.private.ids
    security_group_ids = [data.aws_security_group.codebuild_sg.id]
  }]

  common_tags = {
    "pxp.application" = "Pay"
  }
}

###################################################################################
# CodePipeline job                                                                #
###################################################################################
resource "aws_codepipeline" "payreporting_service_codepipeline" {

  name     = local.payreporting_service.name
  role_arn = aws_iam_role.payreporting_service.arn

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
        BranchName           = local.payreporting_service.codecommit_branch
        RepositoryName       = var.repository_name
        PollForSourceChanges = local.payreporting_service.PollForSourceChanges
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
        ProjectName = module.payreporting_service_codebuild.codebuild_project.name
      }
    }
  }

  tags = {
    "pxp.application" = "Pay"
  }
}

###################################################################################
# Pipeline trigger                                                                #
###################################################################################
resource "aws_cloudwatch_event_rule" "trigger_payreporting_service_codepipeline" {
  name        = "${local.payreporting_service.name}-trigger"
  description = "Trigger pipeline execution for ${local.payreporting_service.name} when changes are pushed to ${local.payreporting_service.codecommit_branch} branch in CodeCommit repo ${var.repository_name}."

  event_pattern = <<EOF
{
  "source": ["aws.codecommit"],
  "detail-type": ["CodeCommit Repository State Change"],
  "resources": ["arn:aws:codecommit:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:${var.repository_name}"],
  "detail": {
    "referenceType": ["branch"],
    "referenceName": ["${local.payreporting_service.codecommit_branch}"],
    "event": ["referenceDeleted", "referenceCreated"]
  }
}
EOF

  tags = {
    "pxp.application" = "Pay"
  }
}

resource "aws_cloudwatch_event_target" "trigger_payreporting_service_codepipeline" {
  rule      = aws_cloudwatch_event_rule.trigger_payreporting_service_codepipeline.name
  target_id = "${local.payreporting_service.name}-event-target"
  role_arn  = aws_iam_role.payreporting_service_cwevent.arn
  arn       = aws_codepipeline.payreporting_service_codepipeline.arn
}

###################################################################################
# IAM policies and roles                                                          #
###################################################################################

# IAM policies and roles for setting up Codepipeline triggers
resource "aws_iam_role" "payreporting_service_cwevent" {
  name               = "${local.payreporting_service.name}-cwevent"
  assume_role_policy = data.aws_iam_policy_document.cwevent_assume_role_policy.json

  tags = {
    "pxp.application" = "Pay"
  }
}

data "aws_iam_policy_document" "payreporting_service_cwevent" {
  statement {
    actions = [
      "codepipeline:StartPipelineExecution",
    ]

    resources = [
      aws_codepipeline.payreporting_service_codepipeline.arn,
    ]
  }
}

resource "aws_iam_role_policy" "payreporting_service_cw_events_policy" {
  name   = "${local.payreporting_service.name}-cwevents-policy"
  role   = aws_iam_role.payreporting_service_cwevent.name
  policy = data.aws_iam_policy_document.payreporting_service_cwevent.json
}

# IAM policies and roles for CodePipeline
resource "aws_iam_role" "payreporting_service" {

  name               = "${local.payreporting_service.name}-role"
  assume_role_policy = data.aws_iam_policy_document.pipeline_assume_role_policy.json
}

data "aws_iam_policy_document" "payreporting_service_codebuild" {

  statement {
    sid = "Builds"

    actions = [
      "codebuild:BatchGetBuilds",
      "codebuild:StartBuild",
      "codebuild:BatchGetProjects",
    ]

    resources = [
      module.payreporting_service_codebuild.codebuild_project.arn
    ]
  }
}

data "aws_iam_policy_document" "payreporting_service_codeartifact_token" {
  # CodeBuild project needs the below IAM permissions to get authentication token from CodeArtifact and upload packages to it

  statement {
    sid = "StsPermissions"
    actions = [
      "sts:GetServiceBearerToken"
    ]
    resources = [
      "arn:aws:sts::${data.aws_caller_identity.current.id}:assumed-role/${module.payreporting_service_codebuild.codebuild_role.name}/*"
    ]
    condition {
      test     = "StringEquals"
      variable = "sts:AWSServiceName"
      values   = ["codeartifact.amazonaws.com"]
    }
  }

  statement {
    sid = "CodeArtifactDomainPermissions"
    actions = [
      "codeartifact:GetAuthorizationToken"
    ]
    resources = ["arn:aws:codeartifact:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:domain/${var.codeartifact_maven_domain}"]
  }

  statement {
    sid = "CodeArtifactRepositoryPermissions"
    actions = [
      "codeartifact:DescribeRepository",
      "codeartifact:ReadFromRepository",
      "codeartifact:GetRepositoryEndpoint",
      "codeartifact:UpdateRepository"
    ]
    resources = ["arn:aws:codeartifact:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:repository/${var.codeartifact_maven_domain}/${var.codeartifact_maven_repo}"]
  }

  statement {
    sid = "CodeArtifactPackagePermissions"
    actions = [
      "codeartifact:UpdatePackageVersionsStatus",
      "codeartifact:PublishPackageVersion",
      "codeartifact:PutPackageMetadata"
    ]
    resources = ["arn:aws:codeartifact:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:package/${var.codeartifact_maven_domain}/${var.codeartifact_maven_repo}/*/*/*"]
  }
}

resource "aws_iam_role_policy" "payreporting_service_pipeline" {

  name   = "${local.payreporting_service.name}-pipeline-policy"
  role   = aws_iam_role.payreporting_service.name
  policy = data.aws_iam_policy_document.common_codepipeline_permissions.json
}

resource "aws_iam_role_policy" "payreporting_service_codebuild" {

  name   = "${local.payreporting_service.name}-codebuild-policy"
  role   = aws_iam_role.payreporting_service.name
  policy = data.aws_iam_policy_document.payreporting_service_codebuild.json
}

resource "aws_iam_role_policy" "payreporting_service_codeartifact_token_inline_policy" {
  name   = "${local.payreporting_service.name}-codeartifact-token-inline-policy"
  role   = module.payreporting_service_codebuild.codebuild_role.name
  policy = data.aws_iam_policy_document.payreporting_service_codeartifact_token.json
}
