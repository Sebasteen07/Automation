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

data "aws_iam_policy_document" "cwevent_assume_role_policy" {

  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type = "Service"

      identifiers = ["events.amazonaws.com"]
    }
  }
}

