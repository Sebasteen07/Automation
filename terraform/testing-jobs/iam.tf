# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
data "aws_iam_policy_document" "pipeline_assume_role_policy" {
  provider = aws.pipeline

  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["codepipeline.amazonaws.com"]
    }

  }
}

resource "aws_iam_role" "pipeline" {
  provider = aws.pipeline

  name               = "${local.name}-role"
  assume_role_policy = data.aws_iam_policy_document.pipeline_assume_role_policy.json

  tags = local.common_tags
}

data "aws_iam_policy_document" "pipeline" {
  provider = aws.pipeline

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
      local.aws_codebuild_project_arn,
    ]
  }

  statement {
    sid = "Source"

    actions = [
      "codecommit:Get*",
      "codecommit:UploadArchive",
    ]

    resources = [
      local.aws_codecommit_repo_arn,
    ]
  }
}

resource "aws_iam_role_policy" "pipeline" {
  provider = aws.pipeline

  name   = "pipeline"
  role   = aws_iam_role.pipeline.name
  policy = data.aws_iam_policy_document.pipeline.json
}
