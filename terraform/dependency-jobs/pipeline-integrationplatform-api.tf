# Copyright 2022 NXGN Management, LLC. All Rights Reserved.

###################################################################################
# CodeBuild project                                                               #
###################################################################################
module "integrationplatform_api_codebuild" {
  source = "git::ssh://git@bitbucket.nextgen.com:7999/dope/codebuild?ref=4.3.0"

  artifact_bucket_arns = [local.pipeline_artifact_bucket_arn]
  name                 = local.integrationplatform_api.name
  kms_key_id           = local.kms_key_id
  artifact_kms_key_ids = []
  artifacts_type       = var.codebuild_artifacts_type

  environment_variables = [
    {
      name  = "maven_command"
      value = "${local.integrationplatform_api.maven_parameter} -Dmaven.test.skip=${local.integrationplatform_api.maven_test_skip}"
      type  = "PLAINTEXT"
    },
    {
      name  = "execution_folder"
      value = local.integrationplatform_api.execution_folder
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
  build_timeout    = local.integrationplatform_api.build_timeout
  queued_timeout   = local.integrationplatform_api.queued_timeout

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
    "pxp.application" = "Portal"
  }
}

###################################################################################
# CodePipeline job                                                                #
###################################################################################
resource "aws_codepipeline" "integrationplatform_api_codepipeline" {

  name     = local.integrationplatform_api.name
  role_arn = aws_iam_role.integrationplatform_api.arn

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
        BranchName           = local.integrationplatform_api.codecommit_branch
        RepositoryName       = var.repository_name
        PollForSourceChanges = local.integrationplatform_api.PollForSourceChanges
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
        ProjectName = module.integrationplatform_api_codebuild.codebuild_project.name
      }
    }
  }

  tags = {
    "pxp.application" = "Portal"
  }
}

###################################################################################
# Pipeline trigger                                                                #
###################################################################################
resource "aws_cloudwatch_event_rule" "trigger_integrationplatform_api_codepipeline" {
  name        = "${local.integrationplatform_api.name}-trigger"
  description = "Trigger pipeline execution for ${local.integrationplatform_api.name} when changes are pushed to ${local.integrationplatform_api.codecommit_branch} branch in CodeCommit repo ${var.repository_name}."

  event_pattern = <<EOF
{
  "source": ["aws.codecommit"],
  "detail-type": ["CodeCommit Repository State Change"],
  "resources": ["arn:aws:codecommit:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:${var.repository_name}"],
  "detail": {
    "referenceType": ["branch"],
    "referenceName": ["${local.integrationplatform_api.codecommit_branch}"],
    "event": ["referenceDeleted", "referenceCreated"]
  }
}
EOF

  tags = {
    "pxp.application" = "Portal"
  }
}

resource "aws_cloudwatch_event_target" "trigger_integrationplatform_api_codepipeline" {
  rule      = aws_cloudwatch_event_rule.trigger_integrationplatform_api_codepipeline.name
  target_id = "${local.integrationplatform_api.name}-event-target"
  role_arn  = aws_iam_role.integrationplatform_api_cwevent.arn
  arn       = aws_codepipeline.integrationplatform_api_codepipeline.arn
}

###################################################################################
# IAM policies and roles                                                          #
###################################################################################

# IAM policies and roles for setting up Codepipeline triggers
resource "aws_iam_role" "integrationplatform_api_cwevent" {
  name               = "${local.integrationplatform_api.name}-cwevent"
  assume_role_policy = data.aws_iam_policy_document.cwevent_assume_role_policy.json

  tags = {
    "pxp.application" = "Portal"
  }
}

data "aws_iam_policy_document" "integrationplatform_api_cwevent" {
  statement {
    actions = [
      "codepipeline:StartPipelineExecution",
    ]

    resources = [
      aws_codepipeline.integrationplatform_api_codepipeline.arn,
    ]
  }
}

resource "aws_iam_role_policy" "integrationplatform_api_cw_events_policy" {
  name   = "${local.integrationplatform_api.name}-cwevents-policy"
  role   = aws_iam_role.integrationplatform_api_cwevent.name
  policy = data.aws_iam_policy_document.integrationplatform_api_cwevent.json
}

# IAM policies and roles for CodePipeline
resource "aws_iam_role" "integrationplatform_api" {

  name               = "${local.integrationplatform_api.name}-role"
  assume_role_policy = data.aws_iam_policy_document.pipeline_assume_role_policy.json
}

data "aws_iam_policy_document" "integrationplatform_api_codebuild" {

  statement {
    sid = "Builds"

    actions = [
      "codebuild:BatchGetBuilds",
      "codebuild:StartBuild",
      "codebuild:BatchGetProjects",
    ]

    resources = [
      module.integrationplatform_api_codebuild.codebuild_project.arn
    ]
  }
}

data "aws_iam_policy_document" "integrationplatform_api_codeartifact_token" {
  # CodeBuild project needs the below IAM permissions to get authentication token from CodeArtifact and upload packages to it

  statement {
    sid = "StsPermissions"
    actions = [
      "sts:GetServiceBearerToken"
    ]
    resources = [
      "arn:aws:sts::${data.aws_caller_identity.current.id}:assumed-role/${module.integrationplatform_api_codebuild.codebuild_role.name}/*"
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

resource "aws_iam_role_policy" "integrationplatform_api_pipeline" {

  name   = "${local.integrationplatform_api.name}-pipeline-policy"
  role   = aws_iam_role.integrationplatform_api.name
  policy = data.aws_iam_policy_document.common_codepipeline_permissions.json
}

resource "aws_iam_role_policy" "integrationplatform_api_codebuild" {

  name   = "${local.integrationplatform_api.name}-codebuild-policy"
  role   = aws_iam_role.integrationplatform_api.name
  policy = data.aws_iam_policy_document.integrationplatform_api_codebuild.json
}

resource "aws_iam_role_policy" "integrationplatform_api_codeartifact_token_inline_policy" {
  name   = "${local.integrationplatform_api.name}-codeartifact-token-inline-policy"
  role   = module.integrationplatform_api_codebuild.codebuild_role.name
  policy = data.aws_iam_policy_document.integrationplatform_api_codeartifact_token.json
}
