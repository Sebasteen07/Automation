# Copyright 2021 NXGN Management, LLC. All Rights Reserved.
data "aws_iam_policy_document" "pipeline_assume_role_policy" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["codepipeline.amazonaws.com"]
    }
  }
}

resource "aws_iam_role" "pipeline" {
  name               = "${local.name}-role"
  assume_role_policy = data.aws_iam_policy_document.pipeline_assume_role_policy.json
}

data "aws_iam_policy_document" "pipeline" {
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
      module.build.codebuild_project.arn
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

resource "aws_iam_role_policy" "pipeline" {
  name   = "pipeline"
  role   = aws_iam_role.pipeline.name
  policy = data.aws_iam_policy_document.pipeline.json
}
